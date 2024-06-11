package org.bbsgroup.bbs.controller.app;

import jakarta.servlet.http.HttpServletRequest;
import org.bbsgroup.bbs.dao.CategoryDao;
import org.bbsgroup.bbs.dao.PostDao;
import org.bbsgroup.bbs.entity.Category;
import org.bbsgroup.bbs.entity.Post;
import org.bbsgroup.bbs.util.PageQueryUtil;
import org.bbsgroup.bbs.util.PageResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/app")
public class PageController {
    private final CategoryDao categoryDao;
    private final PostDao postDao;

    public PageController(CategoryDao categoryDao, PostDao postDao) {
        this.categoryDao = categoryDao;
        this.postDao = postDao;
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
        List<Post> postList = postDao.selectByCategoryIdAndPage(pageUtil);

        PageResult pageResult = new PageResult(postList, total, pageUtil.getLimit(), pageUtil.getPage());
        request.setAttribute("pageResult", pageResult);
        return "index";
    }
}
