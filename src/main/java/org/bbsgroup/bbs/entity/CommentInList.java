package org.bbsgroup.bbs.entity;

import java.time.LocalDateTime;

public class CommentInList {

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

    /**
     * 评论用户名
     */
    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "CommentInList{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", postId=" + postId +
                ", parentId=" + parentId +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", username='" + username + '\'' +
                '}';
    }
}
