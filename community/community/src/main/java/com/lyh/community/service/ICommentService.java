package com.lyh.community.service;

import com.github.pagehelper.PageInfo;
import com.lyh.community.entity.Resp;
import com.lyh.community.entity.Comment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ICommentService {
    Resp<Comment> createComment(String content, String email, String password, String tname, String mname);//后台需要给评论安排序号

    Resp<Comment> deleteComment(int num, String email, String password, String tname, String mname);

    Resp<PageInfo<Comment>> showCommentByTopic(String tname, int pageNum, int pageSize, String mname);

    Resp<Comment> likeComment(String email, String password, String tname, int num, String mname);//需要验证likeComment表
}