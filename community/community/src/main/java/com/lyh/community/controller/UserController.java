package com.lyh.community.controller;

import com.lyh.community.entity.Resp;
import com.lyh.community.entity.User;
import com.lyh.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    private Resp<User> login(String email, String password){
        if(email.length()>40||password.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iUserService.login(email, password);
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    private Resp<User> register(String uname, String password, String email, String code){
        if(email.length()>40||password.length()>40){ //||uname.length()>20||code.length()>4){
            return Resp.fail("777","参数错误！");
        }
        System.out.print(password);
        return iUserService.register(uname,password,email);
    }

    @RequestMapping(value = "/modifybasic",method = RequestMethod.POST)
    private Resp<User> modifybasic(String email, String uname, String password, byte sex, byte age, String motto){
        if(email.length()>40||password.length()>40||uname.length()>20||(sex!=0&&sex!=1&&sex!=2)||age>120||age<0||motto.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iUserService.modifybasic(email, uname, password, sex, age, motto);
    }

    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    private Resp<User> changePassword(String email, String oldpassword, String newpassword){
        if(email.length()>40||oldpassword.length()>40||newpassword.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iUserService.changePassword(email, oldpassword, newpassword);
    }

    @RequestMapping(value = "/changeEmail",method = RequestMethod.POST)
    private Resp<User> changeEmail(String oldemail, String newemail, String password, String code){
        if(oldemail.length()>40||password.length()>40||newemail.length()>40||code.length()>4){
            return Resp.fail("777","参数错误！");
        }
        if(!redisTemplate.hasKey(newemail)||!redisTemplate.opsForValue().get(newemail).equals(code)){
            return Resp.fail("666","验证码错误！");
        }else{
            redisTemplate.opsForValue().getOperations().delete(newemail);
        }
        return iUserService.changeEmail(oldemail, newemail, password);
    }

    @RequestMapping(value = "/showInfo",method = RequestMethod.POST)
    private Resp<User> showInfo(String email, String password){
        if(email.length()>40||password.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iUserService.showInfo(email, password);
    }
}
