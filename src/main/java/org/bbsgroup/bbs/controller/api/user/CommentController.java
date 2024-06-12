package org.bbsgroup.bbs.controller.api.user;

import jakarta.servlet.http.HttpSession;
import org.bbsgroup.bbs.common.Constants;
import org.bbsgroup.bbs.dao.CommentDao;
import org.bbsgroup.bbs.dao.PostDao;
import org.bbsgroup.bbs.dao.UserDao;
import org.bbsgroup.bbs.entity.Comment;
import org.bbsgroup.bbs.entity.Post;
import org.bbsgroup.bbs.entity.User;
import org.bbsgroup.bbs.util.Result;
import org.bbsgroup.bbs.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Controller
@RequestMapping("/api/user")
@Transactional
public class CommentController {

    private final PostDao postDao;
    private final UserDao userDao;
    private final CommentDao commentDao;

    public CommentController(PostDao postDao, UserDao userDao, CommentDao commentDao) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.commentDao = commentDao;
    }

    @PostMapping("/replyPost")
    @ResponseBody
    public Result addComment(@RequestParam Integer postId, @RequestParam Integer userId, @RequestParam String content, HttpSession session) {

        User user = (User) session.getAttribute(Constants.USER_SESSION_KEY);

        if (ObjectUtils.isEmpty(postId)) {
            return ResultGenerator.genFailResult("postId 不能为空！");
        }
        if (!user.getUserId().equals(userId)) {
            return ResultGenerator.genUnAuthorizedResult("未登录，无评论权限！");
        }
        if (!StringUtils.hasLength(content)) {
            return ResultGenerator.genFailResult("评论内容不能为空！");
        }
        if (content.trim().length() > 200) {
            return ResultGenerator.genFailResult("评论内容过长！");
        }

        Comment comment = new Comment();

        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setCreateTime(new Date());

        Post post = postDao.selectPostByPostId(comment.getPostId());
        User user1 = userDao.selectUserByUserId(comment.getUserId());

        if (ObjectUtils.isEmpty(post) || ObjectUtils.isEmpty(user1)) {
            return ResultGenerator.genBadRequestResult("用户或帖子不存在，请重试！");
        }

        try {
            commentDao.insertComment(comment);
            return ResultGenerator.genSuccessResult(null);
        } catch (Exception e) {
            return ResultGenerator.genInternalServerError("服务器未知错误！");
        }
    }

    @PostMapping("/delReply/{commentId}")
    @ResponseBody
    public Result delPostComment(@PathVariable("commentId") Integer commentId, HttpSession session) {
        Comment comment = commentDao.getCommentByCommentId(commentId);

        if (ObjectUtils.isEmpty(comment)) {
            return ResultGenerator.genBadRequestResult("评论不存在！");
        }

        Post post = postDao.selectPostByPostId(comment.getPostId());

        //  评论所关联的帖子不存在，不能删除
        if (ObjectUtils.isEmpty(post)) {
            return ResultGenerator.genBadRequestResult("帖子不存在！");
        }

        User user = (User) session.getAttribute(Constants.USER_SESSION_KEY);
        Integer userId = user.getUserId();

        if (userId.equals(comment.getUserId()) || userId.equals(post.getUserId())) {

            //  评论者和发帖者都可以删评
            try {
                commentDao.deleteCommentByCommentId(commentId);
                return ResultGenerator.genSuccessResult(null);
            } catch (Exception e) {
                return ResultGenerator.genInternalServerError("服务器未知错误！");
            }
        }

        return ResultGenerator.genUnAuthorizedResult("没有删贴权限！");
    }
}
