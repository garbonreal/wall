package com.lyh.community.controller;

import com.github.pagehelper.PageInfo;
import com.lyh.community.entity.Comment;
import com.lyh.community.entity.Resp;
import com.lyh.community.entity.User;
import com.lyh.community.service.ICommentService;
import com.lyh.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    
    @Autowired
    private ICommentService iCommentService;
    
    @RequestMapping(value = "/createComment", method = RequestMethod.POST)
    private Resp<Comment> createComment(String content, String email, String password, String ttime, int ccanonymous, String ctime, String mname) {
        if (email.length() > 40 || password.length() > 40 || ctime.length() > 40 || content.length() > 10000) {
            return Resp.fail("777", "参数错误！");
        }
        return iCommentService.createComment(content, email, password, ttime, ccanonymous, ctime, mname);
    }
    
    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
    private Resp<Comment> deleteComment(String email, String password, String ttime, String mname, String ctime) {
        if (email.length() > 40 || password.length() > 40 || ttime.length() > 40) {
            return Resp.fail("777", "参数错误！");
        }
        return iCommentService.deleteComment(email, password, ttime, mname, ctime);
    }
    
    @RequestMapping(value = "/showCommentByTopic", method = RequestMethod.POST)
    private Resp<PageInfo<Comment>> showCommentByTopic(String ttime, int pageNum, int pageSize, String mname) {
        if (ttime.length() > 40) {
            return Resp.fail("777", "参数错误！");
        }
        return iCommentService.showCommentByTopic(ttime, pageNum, pageSize, mname);
    }
    
    //点赞评论
    @RequestMapping(value = "/likeComment", method = RequestMethod.POST)
    private Resp<Comment> likeComment(String email, String password, String ttime,String ctime, String mname) {
        if (email.length() > 40 || password.length() > 40) {
            return Resp.fail("777", "参数错误！");
        }
        return iCommentService.likeComment(email, password, ttime, ctime, mname);
    }
}
