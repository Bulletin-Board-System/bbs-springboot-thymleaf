package org.bbsgroup.bbs.service.impl;

import org.bbsgroup.bbs.entity.Comment;
import org.bbsgroup.bbs.mapper.CommentMapper;
import org.bbsgroup.bbs.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2024-06-11
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
