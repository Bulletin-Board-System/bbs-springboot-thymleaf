package org.bbsgroup.bbs.dao;

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
            "<script> select * from bbs.post",
            "where category_id = #{categoryId}",
            "order by is_pinned desc",
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
}
