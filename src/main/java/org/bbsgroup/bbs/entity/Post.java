package org.bbsgroup.bbs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 帖子表
 * </p>
 *
 * @author https://github.com/lukrisum
 * @since 2024-06-11
 */
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "post_id", type = IdType.AUTO)
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
     * 内容
     */
    private String content;

    /**
     * 发表时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

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

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Post{" +
        "postId = " + postId +
        ", userId = " + userId +
        ", categoryId = " + categoryId +
        ", isPinned = " + isPinned +
        ", isFeatured = " + isFeatured +
        ", title = " + title +
        ", content = " + content +
        ", createTime = " + createTime +
        ", updateTime = " + updateTime +
        "}";
    }
}
