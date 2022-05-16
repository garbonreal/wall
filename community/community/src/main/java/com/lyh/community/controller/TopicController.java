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
    private Resp<Topic> createTopic(String intro, String email, String password, int tanonymous, String date, String mname){
        if(email.length()>40||password.length()>40||intro.length()>100||mname.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iTopicService.createTopic(intro, email, password, tanonymous, date, mname);
    }

    @RequestMapping(value = "/deleteTopic",method = RequestMethod.POST)
    private Resp<Topic> deleteTopic(String tname, String email, String password, String mname){//////////////////////////////////////////
        if(email.length()>40||password.length()>40||tname.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iTopicService.deleteTopic(tname, email, password, mname);
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
    private Resp<Topic> likeTopic(String email, String password, String tname, String mname){/////////////////////////////////////
        if(email.length()>40||password.length()>40||tname.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iTopicService.likeTopic(email, password, tname, mname);
    }

    @RequestMapping(value = "/favorite",method = RequestMethod.POST)
    private Resp<Topic> favorite(String email, String password, String tname, String mname){////////////////////////////////////////
        if(email.length()>40||password.length()>40||tname.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iTopicService.favorite(email, password, tname, mname);
    }
}
