package org.bbsgroup.bbs.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bbsgroup.bbs.entity.Category;

import java.util.List;

@Mapper
public interface CategoryDao {
    @Select("select * from bbs.category")
    List<Category> selectAll();
}
