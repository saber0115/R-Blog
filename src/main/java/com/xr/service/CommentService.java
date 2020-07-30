package com.xr.service;

import com.xr.pojo.Comment;

import java.util.List;

/**
 * @author: xr
 * @create: 2020/7/26
 * @describe:
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
