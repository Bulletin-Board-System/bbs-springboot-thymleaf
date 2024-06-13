package org.bbsgroup.bbs.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpSession;
import org.bbsgroup.bbs.common.Constants;
import org.bbsgroup.bbs.dao.CategoryDao;
import org.bbsgroup.bbs.dao.CommentDao;
import org.bbsgroup.bbs.dao.PostDao;
import org.bbsgroup.bbs.dao.UserDao;
import org.bbsgroup.bbs.entity.*;
import org.bbsgroup.bbs.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/admin")
@Transactional
public class PostAdminController {
    private final PostDao postDao;
    private final CategoryDao categoryDao;
    private final UserDao userDao;
    private final CommentDao commentDao;

    public PostAdminController(PostDao postDao, HttpSession httpSession, CategoryDao categoryDao, UserDao userDao, CommentDao commentDao) {
        this.postDao = postDao;
        this.categoryDao = categoryDao;
        this.userDao = userDao;
        this.commentDao = commentDao;
    }

    @PostMapping("/editPost")
    @ResponseBody
    public Result editPost(
            @RequestBody JSONObject jsonParam
    ) {
        Integer postId = jsonParam.getInteger("postId");
        String title = jsonParam.getString("title");
        Integer categoryId = jsonParam.getInteger("categoryId");
        String content = jsonParam.getString("content");
        Byte isPinned = jsonParam.getByte("isPinned");
        Byte isFeatured = jsonParam.getByte("isFeatured");

        //  是否为空值
        if (ObjectUtils.isEmpty(postId)) {
            return ResultGenerator.genBadRequestResult("文章 id 不能为空！");
        }

        Post post = postDao.selectPostByPostId(postId);


        if (!StringUtils.hasLength(title)) {
            return ResultGenerator.genBadRequestResult("标题不能为空！");
        }

        if (ObjectUtils.isEmpty(categoryId)) {
            return ResultGenerator.genBadRequestResult("类别不能为空！");
        }

        if (!StringUtils.hasLength(content)) {
            return ResultGenerator.genBadRequestResult("内容不能为空！");
        }

        if (title.trim().length() > 32) {
            return ResultGenerator.genFailResult("标题过长");
        }
        if (content.trim().length() > 100000) {
            return ResultGenerator.genFailResult("内容过长");
        }

        post.setTitle(title);
        post.setContent(content);
        post.setIsPinned(isPinned);
        post.setIsFeatured(isFeatured);
        post.setCategoryId(categoryId);
        post.setUpdateTime(new Date());

        try {
            postDao.updatePostByPostId(post);
            return ResultGenerator.genSuccessResult(null);
        } catch (Exception e) {
            return ResultGenerator.genInternalServerError("服务器未知错误！");
        }
    }

    // 删除文章
    @PostMapping("/delPost/{postId}")
    @ResponseBody
    public Result delPost(@PathVariable("postId") Integer postId) {
        if (null == postId || postId < 0) {
            return ResultGenerator.genFailResult("文章 id 不能为空！");
        }

        try {
            commentDao.deleteCommentByPostId(postId);
            postDao.deletePostByPostId(postId);
            return ResultGenerator.genSuccessResult(null);
        } catch (Exception e) {
            return ResultGenerator.genFailResult("服务器未知错误！");
        }
    }

    // 查询所有文章
    @PostMapping("/posts")
    @ResponseBody
    public Result selectPost(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page){
        //  分页参数
        Map params = new HashMap();
        params.put("page", page);
        //  默认分页大小为 10
        params.put("limit", 10);

        PageQueryUtil pageUtil = new PageQueryUtil(params);

        int total = postDao.getCountByCategoryId(pageUtil);
        List<Post> postOriginalList = postDao.selectByCategoryIdAndPage(pageUtil);
        List<PostInList> postList = new ArrayList<>();

        //  分别批量请求，组装成 PostInList 类型（方便最终展示）
        if (!CollectionUtils.isEmpty(postOriginalList)) {
            postList = BeanUtil.copyList(postOriginalList, PostInList.class);

            List<Integer> userIds = postList.stream().map(PostInList::getUserId).collect(Collectors.toList());
            //  查询 user 数据
            List<User> users = userDao.selectAllByUserIds(userIds);
            if (!CollectionUtils.isEmpty(users)) {
                //封装 user 数据
                Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(User::getUserId, Function.identity(), (entity1, entity2) -> entity1));
                for (PostInList post : postList) {
                    if (userMap.containsKey(post.getUserId())) {
                        // 获取 user 字段
                        User user = userMap.get(post.getUserId());
                        post.setUsername(user.getUsername());
                    }
                }
            }

            List<Integer> categoryIds = postList.stream().map(PostInList::getCategoryId).collect(Collectors.toList());
            // 查询 category 数据
            List<Category> categories = categoryDao.selectAllByCategoryIds(categoryIds);
            if (!CollectionUtils.isEmpty(categories)) {
                //封装 category 数据
                Map<Integer, Category> categoryMap = categories.stream().collect(Collectors.toMap(Category::getCategoryId, Function.identity(), (entity1, entity2) -> entity1));
                for (PostInList post : postList) {
                    if (categoryMap.containsKey(post.getCategoryId())) {
                        // 获取 category 字段
                        Category category = categoryMap.get(post.getCategoryId());
                        post.setCategory(category.getName());
                    }
                }
            }

            List<Integer> postIds = postList.stream().map(PostInList::getPostId).collect(Collectors.toList());
            List<PostInList> postWithCommentCountList = commentDao.getCommentCountByPostIds(postIds);
            if (!CollectionUtils.isEmpty(postIds)) {
                //封装 commentCount 数据
                Map<Integer, PostInList> commentCountMap = postWithCommentCountList.stream().collect(Collectors.toMap(PostInList::getPostId, Function.identity(), (entity1, entity2) -> entity1));
                for (PostInList post : postList) {
                    if (commentCountMap.containsKey(post.getPostId())) {
                        // 获取 commentCount 字段
                        Integer commentCount = commentCountMap.get(post.getPostId()).getCommentCount();
                        post.setCommentCount(commentCount);
                    }
                }
            }
        }

        PageResult pageResult = new PageResult(postList, total, pageUtil.getLimit(), pageUtil.getPage());
        return ResultGenerator.genSuccessResult(pageResult);
    }

    // 根据id查询文章详情
    @PostMapping("/detail/{postId}")
    @ResponseBody
    public Result selectPostByPostId(@PathVariable("postId") Integer postId){
        if (null == postId || postId < 0) {
            return ResultGenerator.genFailResult("文章 id 不能为空！");
        }

        Post post = postDao.selectPostByPostId(postId);
        return ResultGenerator.genSuccessResult(post);
    }

    //  获取分区列表
    @GetMapping("/categories")
    @ResponseBody
    public Result getCategories(){
        List<Category> categories = categoryDao.selectAll();
        return ResultGenerator.genSuccessResult(categories);
    }
}
