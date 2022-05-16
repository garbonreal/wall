package com.lyh.community.service;

import com.lyh.community.entity.Resp;
import com.lyh.community.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserService {
    Resp<User> login(String email, String password);//使用邮箱和密码登录

    Resp<User> register(String uname, String password, String email);//注册时提供用户名、密码、邮箱

    Resp<User> modifybasic(String email, String uname, String password, byte sex, byte age, String motto);//修改基本信息

    Resp<User> changePassword(String email, String oldpassword, String newpassword);//修改密码

    Resp<User> changeEmail(String oldemail, String newemail, String password);//修改邮箱

    Resp<User> showInfo(String email, String password);//查看个人信息
}
