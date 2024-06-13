package org.bbsgroup.bbs.controller.api.admin;

import jakarta.servlet.http.HttpSession;
import org.bbsgroup.bbs.dao.PostDao;
import org.bbsgroup.bbs.entity.Post;
import org.bbsgroup.bbs.util.Result;
import org.bbsgroup.bbs.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/api/admin")
@Transactional
public class PostAdminController {
    private final PostDao postDao;
    public PostAdminController(PostDao postDao, HttpSession httpSession) {
        this.postDao = postDao;
    }


    @PostMapping("/editPost")
    @ResponseBody
    public Result editPost(
            @RequestParam("postId") Integer postId,
            @RequestParam("title") String title,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("content") String content,
            @RequestParam("isPinned") Byte isPinned,
            @RequestParam("isFeatured") Byte isFeatured
    ) {
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

    @PostMapping("/delPost/{postId}")
    @ResponseBody
    public Result delPost(@PathVariable("postId") Integer postId,
                          HttpSession httpSession) {
        if (null == postId || postId < 0) {
            return ResultGenerator.genFailResult("文章 id 不能为空！");
        }


        try{
            postDao.deletePostByPostId(postId);
            return ResultGenerator.genSuccessResult(null);
        }catch (Exception e){
            return ResultGenerator.genFailResult("服务器未知错误！");
        }
    }
}

