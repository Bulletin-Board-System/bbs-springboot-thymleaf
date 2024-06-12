package org.bbsgroup.bbs.entity;

import java.util.Date;

public class PostInList {
    private static final long serialVersionUID = 1L;

    private Integer postId;

    /**
     * 发帖用户ID
     */
    private Integer userId;

    /**
     * 帖子板块ID
     */
    private Integer categoryId;

    /**
     * 0表示不置顶，1表示置顶
     */
    private Byte isPinned;

    /**
     * 0表示非精选，1表示精选
     */
    private Byte isFeatured;

    /**
     * 标题
     */
    private String title;

    /**
     * 发表时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 作者用户名
     */
    private String username;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 所在分区
     */
    private String category;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Byte getIsPinned() {
        return isPinned;
    }

    public void setIsPinned(Byte isPinned) {
        this.isPinned = isPinned;
    }

    public Byte getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Byte isFeatured) {
        this.isFeatured = isFeatured;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "PostInList{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                ", isPinned=" + isPinned +
                ", isFeatured=" + isFeatured +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", username='" + username + '\'' +
                ", commentCount=" + commentCount +
                ", category='" + category + '\'' +
                '}';
    }
}
