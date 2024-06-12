package org.bbsgroup.bbs.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bbsgroup.bbs.entity.Comment;
import org.bbsgroup.bbs.entity.PostInList;
import org.bbsgroup.bbs.util.PageQueryUtil;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentDao {
    //  根据 postId 获取评论总数
    @Select("select count(*) from bbs.comment where post_id = #{postId}")
    Integer getCommentCountByPostId(PageQueryUtil pageUtil);

    //  根据 postId 分页获取评论列表
    @Select({
            "<script> select * from bbs.comment",
            "where post_id = #{postId}",
            "order by create_time desc",
            "limit #{start}, #{limit}",
            "</script>"
    })
    List<Comment> getCommentListByPostIdAndPage(PageQueryUtil pageUtil);

    //  根据 postId 列表获取评论数量
    @Select({
            "<script>",
            "select post_id, count(*) as commentCount from bbs.comment where post_id in",
            "<foreach item='postId' collection='postIds' open='(' separator=',' close=')'>",
            "#{postId}",
            "</foreach>",
            "group by post_id",
            "</script>"
    })
    List<PostInList> getCommentCountByPostIds(List<Integer> postIds);

    //  新增评论
    @Insert("insert into bbs.comment (user_id, post_id, parent_id, content, create_time) values (#{userId}, #{postId}, #{parentId}, #{content}, #{createTime})")
    void insertComment(Comment comment);

    //  根据 commentId 获取评论
    @Select("select * from bbs.comment where comment_id = #{commentId}")
    Comment getCommentByCommentId(Integer commentId);

    //  根据 commentId 删除评论
    @Delete("delete from bbs.comment where comment_id = #{commentId}")
    void deleteCommentByCommentId(Integer commentId);
}
