package com.shf.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.blog.common.lang.Result;
import com.shf.blog.entity.Blog;
import com.shf.blog.service.BlogService;
import com.shf.blog.util.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-19
 */
@RestController
public class BlogController {
    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page<Blog> page = new Page<>(currentPage, 5);
        IPage<Blog> pageData = blogService.page(
                page,
                new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.succ(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");

        return Result.succ(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog b;
        if (blog.getId() != null) {
            b = blogService.getById(blog.getId());
//            只能编辑自己的文章
            Assert.isTrue(b.getUserId() == ShiroUtil.getProfile().getId(),"没有权限编辑");

        } else {

            b = new Blog();
            b.setUserId(ShiroUtil.getProfile().getId());
            b.setCreated(LocalDateTime.now());
            b.setStatus(0);
        }

        BeanUtils.copyProperties(blog,b,"id", "userId", "created", "status");
        blogService.saveOrUpdate(b);

        return Result.succ();
    }
}
