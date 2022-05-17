package com.lyh.community.service;

import com.github.pagehelper.PageInfo;
import com.lyh.community.entity.Resp;
import com.lyh.community.entity.Comment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ICommentService {
    Resp<Comment> createComment(String content, String email, String password, String ttime, int ccanonymous, String ctime, String mname);//后台需要给评论安排序号

    Resp<Comment> deleteComment(String email, String password, String ttime, String mname, String ctime);

    Resp<PageInfo<Comment>> showCommentByTopic(String ttime, int pageNum, int pageSize, String mname);
    
    Resp<Comment> likeComment(String email, String password, String ttime, String ctime, String mname);
}