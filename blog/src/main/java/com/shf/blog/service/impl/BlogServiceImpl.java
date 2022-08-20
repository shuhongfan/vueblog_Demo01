package com.shf.blog.service.impl;

import com.shf.blog.entity.Blog;
import com.shf.blog.mapper.BlogMapper;
import com.shf.blog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-19
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
