package org.bbsgroup.bbs.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bbsgroup.bbs.entity.Post;
import org.bbsgroup.bbs.util.PageQueryUtil;

import java.util.List;

@Mapper
public interface PostDao {
    //  获取（分区）帖子总数
    @Select({
            "<script>",
            "select count(*) from bbs.post",
            "<if test='categoryId!=null and categoryId!=\"\"'>",
            "where category_id = #{categoryId}",
            "</if>",
            "</script>"
    })
    Integer getCountByCategoryId(PageQueryUtil pageUtil);

    //  获取（分区）所有帖子
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
}
