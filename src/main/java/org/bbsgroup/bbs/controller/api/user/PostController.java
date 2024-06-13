package org.bbsgroup.bbs.controller.api.user;

import jakarta.servlet.http.HttpSession;
import org.bbsgroup.bbs.common.Constants;
import org.bbsgroup.bbs.dao.CommentDao;
import org.bbsgroup.bbs.dao.PostDao;
import org.bbsgroup.bbs.entity.Post;
import org.bbsgroup.bbs.entity.User;
import org.bbsgroup.bbs.util.Result;
import org.bbsgroup.bbs.util.ResultGenerator;
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
@RequestMapping("/api/user")
@Transactional
public class PostController {
    private final PostDao postDao;
    private final CommentDao commentDao;

    public PostController(PostDao postDao, HttpSession httpSession, CommentDao commentDao) {
        this.postDao = postDao;
        this.commentDao = commentDao;
    }

    @PostMapping("/addPost")
    @ResponseBody
    public Result addPost(
            @RequestParam("title") String title,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("content") String content,
            HttpSession httpSession
    ) {
        //  是否为空值
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

        Post post = new Post();
        User user = (User) httpSession.getAttribute(Constants.USER_SESSION_KEY);

        post.setUserId(user.getUserId());
        post.setTitle(title);
        post.setContent(content);
        post.setCategoryId(categoryId);
        post.setCreateTime(new Date());
        post.setUpdateTime(new Date());

        try {
            postDao.insertPost(post);
            return ResultGenerator.genSuccessResult(null);
        } catch (Exception e) {
            return ResultGenerator.genInternalServerError("服务器未知错误！");
        }
    }

    @PostMapping("/editPost")
    @ResponseBody
    public Result editPost(
            @RequestParam("postId") Integer postId,
            @RequestParam("title") String title,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("content") String content,
            HttpSession httpSession
    ) {
        //  是否为空值
        if (ObjectUtils.isEmpty(postId)) {
            return ResultGenerator.genBadRequestResult("文章 id 不能为空！");
        }

        Post post = postDao.selectPostByPostId(postId);
        User user = (User) httpSession.getAttribute(Constants.USER_SESSION_KEY);

        if (!user.getUserId().equals(post.getUserId())) {
            return ResultGenerator.genFailResult("非本人发帖，无权限修改！");
        }

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
        if (!post.getCategoryId().equals(categoryId)) {
            post.setIsPinned(Byte.valueOf("0"));
        }
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

        User user = (User) httpSession.getAttribute(Constants.USER_SESSION_KEY);
        Post post = postDao.selectPostByPostId(postId);
        if(user.getUserId().equals(post.getPostId())){
            return ResultGenerator.genUnAuthorizedResult("非本人发帖，无权限修改！");
        }

        try{
            commentDao.deleteCommentByPostId(postId);
            postDao.deletePostByPostId(postId);
            return ResultGenerator.genSuccessResult(null);
        }catch (Exception e){
            return ResultGenerator.genFailResult("服务器未知错误！");
        }
    }
}
