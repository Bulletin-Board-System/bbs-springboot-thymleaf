package org.bbsgroup.bbs.service.impl;

import org.bbsgroup.bbs.entity.Category;
import org.bbsgroup.bbs.mapper.CategoryMapper;
import org.bbsgroup.bbs.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 板块表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2024-06-11
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
