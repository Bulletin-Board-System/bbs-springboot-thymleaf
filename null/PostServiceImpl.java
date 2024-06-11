package org.bbsgroup.bbs.service.impl;

import org.bbsgroup.bbs.entity.Post;
import org.bbsgroup.bbs.mapper.PostMapper;
import org.bbsgroup.bbs.service.IPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 帖子表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2024-06-11
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {

}
