package org.bbsgroup.bbs.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bbsgroup.bbs.entity.Category;

import java.util.List;

@Mapper
public interface CategoryDao {
    @Select("select * from bbs.category")
    List<Category> selectAll();

    @Select({
            "<script>",
            "select * from bbs.category where category_id in",
            "<foreach item='categoryId' collection='categoryIds' open='(' separator=',' close=')'>",
            "#{categoryId}",
            "</foreach>",
            "</script>"
    })
    List<Category> selectAllByCategoryIds(List<Integer> categoryIds);
}
