package org.bbsgroup.bbs.service.impl;

import org.bbsgroup.bbs.entity.User;
import org.bbsgroup.bbs.dao.UserMapper;
import org.bbsgroup.bbs.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2024-06-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
