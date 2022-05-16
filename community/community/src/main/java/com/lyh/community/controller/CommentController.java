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

    @RequestMapping(value = "/createComment",method = RequestMethod.POST)
    private Resp<Comment> createComment(String content, String email, String password, String tname, String mname){///////////////
        if(email.length()>40||password.length()>40||tname.length()>40||content.length()>10000){
            return Resp.fail("777","参数错误！");
        }
        return iCommentService.createComment(content, email, password, tname, mname);
    }

    @RequestMapping(value = "/deleteComment",method = RequestMethod.POST)
    private Resp<Comment> deleteComment(int num, String email, String password, String tname, String mname){//////////////////
        if(email.length()>40||password.length()>40||tname.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iCommentService.deleteComment(num, email, password, tname, mname);
    }

    @RequestMapping(value = "/showCommentByTopic",method = RequestMethod.POST)
    private Resp<PageInfo<Comment>> showCommentByTopic(String tname, int pageNum, int pageSize, String mname){//////////////////
        if(tname.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iCommentService.showCommentByTopic(tname, pageNum, pageSize, mname);
    }

    @RequestMapping(value = "/likeComment",method = RequestMethod.POST)
    private Resp<Comment> likeComment(String email, String password, String tname, int num, String mname){//////////////////
        if(email.length()>40||password.length()>40||tname.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iCommentService.likeComment(email, password, tname, num, mname);
    }
}
