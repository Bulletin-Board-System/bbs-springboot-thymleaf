package org.bbsgroup.bbs.entity;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author https://github.com/lukrisum
 * @since 2024-06-11
 */
public class Comment{

    private Integer commentId;

    /**
     * 评论用户ID
     */
    private Integer userId;

    /**
     * 评论的帖子id
     */
    private Integer postId;

    /**
     * 父评论id（null为直接对帖子评论）
     */
    private Integer parentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private LocalDateTime createTime;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
        "commentId = " + commentId +
        ", userId = " + userId +
        ", postId = " + postId +
        ", parentId = " + parentId +
        ", content = " + content +
        ", createTime = " + createTime +
        "}";
    }
}
