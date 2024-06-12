package org.bbsgroup.bbs.controller.app;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.jdbc.Null;
import org.bbsgroup.bbs.dao.CategoryDao;
import org.bbsgroup.bbs.dao.CommentDao;
import org.bbsgroup.bbs.dao.PostDao;
import org.bbsgroup.bbs.dao.UserDao;
import org.bbsgroup.bbs.entity.*;
import org.bbsgroup.bbs.util.BeanUtil;
import org.bbsgroup.bbs.util.PageQueryUtil;
import org.bbsgroup.bbs.util.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app")
public class PageController {
    private static final Logger log = LoggerFactory.getLogger(PageController.class);

    private final CategoryDao categoryDao;
    private final PostDao postDao;
    private final UserDao userDao;
    private final CommentDao commentDao;

    public PageController(CategoryDao categoryDao, PostDao postDao, UserDao userDao, CommentDao commentDao) {
        this.categoryDao = categoryDao;
        this.postDao = postDao;
        this.userDao = userDao;
        this.commentDao = commentDao;
    }

    @GetMapping("/index")
    public String index(HttpServletRequest request,
                        @RequestParam(value = "categoryId", required = false) Integer categoryId,
                        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {

        List<Category> categoryList = categoryDao.selectAll();
        request.setAttribute("categoryList", categoryList);

        //  分页参数
        Map params = new HashMap();
        params.put("page", page);
        //  默认分页大小为 10
        params.put("limit", 10);

        //  帖子类别
        if (categoryId != null && categoryId > 0) {
            request.setAttribute("categoryId", categoryId);
            params.put("categoryId", categoryId);
        }

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
        request.setAttribute("pageResult", pageResult);
        return "index";
    }

    @GetMapping("/detail/{postId}")
    public String detail(HttpServletRequest request, @PathVariable Integer postId,
                         @RequestParam(value = "commentPage", required = false, defaultValue = "1") Integer page) {
        List<Category> categoryList = categoryDao.selectAll();
        request.setAttribute("categoryList", categoryList);

        Post post = postDao.selectPostByPostId(postId);
        request.setAttribute("post", post);

        User user = userDao.selectUserByUserId(post.getUserId());
        request.setAttribute("user", user);

        //  分页参数
        Map params = new HashMap();
        params.put("page", page);
        //  默认分页大小为 6
        params.put("limit", 6);

        PageQueryUtil pageUtil = new PageQueryUtil(params);

        int total = commentDao.getCommentCountByPostId(pageUtil);
        List<Comment> commentOriginalList = commentDao.getCommentListByPostIdAndPage(pageUtil);
        List<CommentInList> commentList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(commentOriginalList)) {
            commentList = BeanUtil.copyList(commentList, CommentInList.class);
            //当前评论者的 userId
            List<Integer> userIds = commentList.stream().map(CommentInList::getUserId).collect(Collectors.toList());
            //分别查询user数据
            List<User> users = userDao.selectAllByUserIds(userIds);
            if (!CollectionUtils.isEmpty(users)) {
                //封装user数据
                Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(User::getUserId, Function.identity(), (entity1, entity2) -> entity1));
                for (CommentInList comment : commentList) {
                    if (userMap.containsKey(comment.getUserId())) {
                        //设置头像字段和昵称字段
                        User user1 = userMap.get(comment.getUserId());
                        comment.setUsername(user1.getUsername());
                    }
                }
            }
        }

        PageResult commentPage = new PageResult(commentList, total, pageUtil.getLimit(), pageUtil.getPage());
        request.setAttribute("commentPage", commentPage);
        return "/post/detail";
    }

    @GetMapping("/appPostPage")
    public String addPost(HttpServletRequest request){
        List<Category> categoryList = categoryDao.selectAll();
        request.setAttribute("categoryList", categoryList);
        return "/post/add";
    }

}
