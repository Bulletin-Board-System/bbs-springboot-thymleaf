package org.bbsgroup.bbs.controller.app;

import jakarta.servlet.http.HttpServletRequest;
import org.bbsgroup.bbs.dao.CategoryDao;
import org.bbsgroup.bbs.dao.PostDao;
import org.bbsgroup.bbs.dao.UserDao;
import org.bbsgroup.bbs.entity.Category;
import org.bbsgroup.bbs.entity.Post;
import org.bbsgroup.bbs.entity.PostPlus;
import org.bbsgroup.bbs.entity.User;
import org.bbsgroup.bbs.util.BeanUtil;
import org.bbsgroup.bbs.util.PageQueryUtil;
import org.bbsgroup.bbs.util.PageResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app")
public class PageController {
    private final CategoryDao categoryDao;
    private final PostDao postDao;
    private final UserDao userDao;

    public PageController(CategoryDao categoryDao, PostDao postDao, UserDao userDao) {
        this.categoryDao = categoryDao;
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @GetMapping("/index")
    public String index(HttpServletRequest request,
                        @RequestParam(value = "categoryId", required = false) Integer categoryId,
                        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {

        //  数据库操作 DAO
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

        //  数据库操作 DAO
        int total = postDao.getCountByCategoryId(pageUtil);
        List<Post> postOriginList = postDao.selectByCategoryIdAndPage(pageUtil);
        List<PostPlus> postList = new ArrayList<>();

        //  分别批量请求，组装成 PostPlus 类型（方便最终展示）
        if (!CollectionUtils.isEmpty(postOriginList)) {
            postList = BeanUtil.copyList(postOriginList,PostPlus.class);

            List<Integer> userIds = postList.stream().map(PostPlus::getUserId).collect(Collectors.toList());
            //  查询 user 数据
            List<User> users = userDao.selectAllByUserIds(userIds);
            if (!CollectionUtils.isEmpty(users)) {
                //封装 user 数据
                Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(User::getUserId, Function.identity(), (entity1, entity2) -> entity1));
                for (PostPlus post : postList) {
                    if (userMap.containsKey(post.getUserId())) {
                        // 获取 user 字段
                        User user = userMap.get(post.getUserId());
                        post.setUsername(user.getUsername());
                    }
                }
            }

            List<Integer> categoryIds = postList.stream().map(PostPlus::getCategoryId).collect(Collectors.toList());
            // 查询 category 数据
            List<Category> categories = categoryDao.selectAllByCategoryIds(categoryIds);
            if (!CollectionUtils.isEmpty(categories)) {
                //封装 category 数据
                Map<Integer, Category> categoryMap = categories.stream().collect(Collectors.toMap(Category::getCategoryId, Function.identity(), (entity1, entity2) -> entity1));
                for (PostPlus post : postList) {
                    if (categoryMap.containsKey(post.getCategoryId())) {
                        // 获取 category 字段
                        Category category = categoryMap.get(post.getCategoryId());
                        post.setCategory(category.getName());
                    }
                }
            }
        }

        PageResult pageResult = new PageResult(postList, total, pageUtil.getLimit(), pageUtil.getPage());
        request.setAttribute("pageResult", pageResult);
        return "index";
    }
}
