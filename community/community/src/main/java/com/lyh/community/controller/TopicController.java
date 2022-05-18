package com.lyh.community.controller;

import com.github.pagehelper.PageInfo;
import com.lyh.community.entity.Resp;
import com.lyh.community.entity.Topic;
import com.lyh.community.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/topic")
public class TopicController {

    @Autowired
    private ITopicService iTopicService;

    @RequestMapping(value = "/createTopic",method = RequestMethod.POST)
    private Resp<Topic> createTopic(String intro, String email, String password, int tanonymous, String ttime, String mname){
        if(email.length()>40||password.length()>40||intro.length()>100||mname.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iTopicService.createTopic(intro, email, password, tanonymous, ttime, mname);
    }

    @RequestMapping(value = "/deleteTopic",method = RequestMethod.POST)
    private Resp<Topic> deleteTopic(String ttime, String email, String password, String mname){//////////////////////////////////////////
        if(email.length()>40||password.length()>40||ttime.length()>20){
            return Resp.fail("777","参数错误！");
        }
        return iTopicService.deleteTopic(ttime, email, password, mname);
    }

    @RequestMapping(value = "/deleteFavoriteTopic",method = RequestMethod.POST)
    private Resp<Topic> deleteFavoriteTopic(String ttime, String email, String password, String mname){//////////////////////////////////////////
        if(email.length()>40||password.length()>40||ttime.length()>20){
            return Resp.fail("777","参数错误！");
        }
        return iTopicService.deleteFavoriteTopic(ttime, email, password, mname);
    }

    @RequestMapping(value = "/showTopicByUid",method = RequestMethod.POST)
    private Resp<PageInfo<Topic>> showTopicByUid(String email, String password, int pageNum, int pageSize){
        if(email.length()>40||password.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iTopicService.showTopicByUid(email, password, pageNum, pageSize);
    }

    @RequestMapping(value = "/showFavoriteByUid",method = RequestMethod.POST)
    private Resp<PageInfo<Topic>> showFavoriteByUid(String email, String password, int pageNum, int pageSize){
        if(email.length()>40||password.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iTopicService.showFavoriteByUid(email, password, pageNum, pageSize);
    }

    @RequestMapping(value = "/showTopicByModule",method = RequestMethod.POST)
    private Resp<PageInfo<Topic>> showTopicByModule(String mname,  int pageNum, int pageSize){
        if(mname.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iTopicService.showTopicByModule(mname, pageNum, pageSize);
    }

    @RequestMapping(value = "/likeTopic",method = RequestMethod.POST)
    private Resp<Topic> likeTopic(String email, String password, String ttime, String mname){
        if(email.length()>40||password.length()>40||ttime.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iTopicService.likeTopic(email, password, ttime, mname);
    }
    
    @RequestMapping(value="/collectTopic",method=RequestMethod.POST)
    private Resp<Topic> collectTopic(String email,String password, String ttime, String mname){
        if(email.length()>40||password.length()>40||ttime.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iTopicService.collectTopic(email, password, ttime, mname);
    }
}
