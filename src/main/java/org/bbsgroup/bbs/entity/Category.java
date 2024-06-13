package org.bbsgroup.bbs.entity;

import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 板块表
 * </p>
 *
 * @author https://github.com/lukrisum
 * @since 2024-06-11
 */
public class Category  {

    private static final long serialVersionUID = 1L;

    private Integer categoryId;

    /**
     * 板块名
     */
    private String name;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
        "categoryId = " + categoryId +
        ", name = " + name +
        "}";
    }
}
