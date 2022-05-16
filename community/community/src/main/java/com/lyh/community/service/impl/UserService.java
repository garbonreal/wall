package com.lyh.community.service.impl;

import com.lyh.community.dao.IUserDao;
import com.lyh.community.entity.Resp;
import com.lyh.community.entity.User;
import com.lyh.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDao iUserDao;

    @Override
    public Resp<User> login(String email, String password) {
        if(iUserDao.selectCountByEmail(email)==0){
            return Resp.fail("401","用户名不存在！");
        }
        else if(iUserDao.selectCountByEmailPassword(email, DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("402","密码错误！");
        }
        return Resp.success(iUserDao.selectAllByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes())));
    }

    @Override
    public Resp<User> register(String uname, String password, String email) {
        if(iUserDao.selectCountByEmail(email)==1){
            return Resp.fail("403","用户名已存在！");
        }
        else{
            iUserDao.insertByEmailPasswordUname(email,DigestUtils.md5DigestAsHex(password.getBytes()),uname);
            return Resp.success(iUserDao.selectAllByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes())));
        }
    }

    @Override
    public Resp<User> modifybasic(String email, String uname, String password, byte sex, byte age, String motto) {
        if(iUserDao.selectCountByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }
        else{
            iUserDao.updateAllExpectEmailPassword(email,uname,sex,age,motto);
            return Resp.success(iUserDao.selectAllByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes())));
        }
    }

    @Override
    public Resp<User> changePassword(String email, String oldpassword, String newpassword) {
        if(iUserDao.selectCountByEmailPassword(email,DigestUtils.md5DigestAsHex(oldpassword.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }
        else{
            iUserDao.updatePassword(email,DigestUtils.md5DigestAsHex(newpassword.getBytes()));
            return Resp.success(iUserDao.selectAllByEmailPassword(email,DigestUtils.md5DigestAsHex(newpassword.getBytes())));
        }
    }

    @Override
    public Resp<User> changeEmail(String oldemail, String newemail, String password) {
        if(iUserDao.selectCountByEmailPassword(oldemail,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }else if(iUserDao.selectCountByEmail(newemail)==1){
            return Resp.fail("403","账户已被注册！");
        } else{
            iUserDao.updateEmail(newemail,oldemail);
            return Resp.success(iUserDao.selectAllByEmailPassword(newemail,DigestUtils.md5DigestAsHex(password.getBytes())));
        }
    }

    @Override
    public Resp<User> showInfo(String email, String password) {
        if(iUserDao.selectCountByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }
        else{
            return Resp.success(iUserDao.selectAllByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes())));
        }
    }
}
