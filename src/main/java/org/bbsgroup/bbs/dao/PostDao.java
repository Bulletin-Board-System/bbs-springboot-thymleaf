package org.bbsgroup.bbs.dao;

import org.apache.ibatis.annotations.*;
import org.bbsgroup.bbs.entity.Post;
import org.bbsgroup.bbs.util.PageQueryUtil;

import java.util.List;

@Mapper
public interface PostDao {
    // 获取全部文章
    @Select("select * from bbs.post")
    List<Post> selectPost();
    //  （根据 categoryId）获取帖子总数
    @Select({
            "<script>",
            "select count(*) from bbs.post",
            "<if test='categoryId!=null and categoryId!=\"\"'>",
            "where category_id = #{categoryId}",
            "</if>",
            "</script>"
    })
    Integer getCountByCategoryId(PageQueryUtil pageUtil);

    //  （根据 categoryId）分页获取帖子列表
    @Select({
            "<script> select * from bbs.post",
            "<if test='categoryId!=null and categoryId!=\"\"'>",
            "where category_id = #{categoryId}",
            "</if>",
            "order by is_pinned desc",
            "limit #{start}, #{limit}",
            "</script>"
    })
    List<Post> selectByCategoryIdAndPage(PageQueryUtil pageUtil);

    //  根据 postId 获取帖子
    @Select("select * from bbs.post where post_id = #{postId}")
    Post selectPostByPostId(Integer postId);

    //  根据 userId 获取帖子列表
    @Select("select * from bbs.post where user_id = #{userId}")
    List<Post> selectPostByUserId(Integer userId);

    //  新建帖子
    @Insert("insert into bbs.post (post_id, user_id, category_id, is_pinned, is_featured, title, content, create_time, update_time) values (#{postId}, #{userId}, #{categoryId}, #{isPinned}, #{isFeatured}, #{title}, #{content}, #{createTime}, #{updateTime})")
    void insertPost(Post post);

    //  更新帖子
    @Update("update bbs.post set category_id = #{categoryId}, title = #{title}, content = #{content}, is_pinned = #{isPinned}, is_featured = #{isFeatured}, update_time = #{updateTime} where post_id = #{postId}")
    void updatePostByPostId(Post post);

    //  删除帖子
    @Delete("delete from bbs.post where post_id = #{postId}")
    void deletePostByPostId(Integer postId);

}