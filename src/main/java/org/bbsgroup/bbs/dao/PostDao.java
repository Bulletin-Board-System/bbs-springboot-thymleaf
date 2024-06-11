package org.bbsgroup.bbs.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bbsgroup.bbs.entity.Post;

import java.util.List;

@Mapper
public interface PostDao {
    @Select("select * from bbs.post where category_id = #{category_id}")
    List<Post> selectAllByCategoryId(String category_id);


}
