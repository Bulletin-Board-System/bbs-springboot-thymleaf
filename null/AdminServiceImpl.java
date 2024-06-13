package org.bbsgroup.bbs.service.impl;

import org.bbsgroup.bbs.entity.Admin;
import org.bbsgroup.bbs.mapper.AdminMapper;
import org.bbsgroup.bbs.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author https://github.com/lukrisum
 * @since 2024-06-11
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
