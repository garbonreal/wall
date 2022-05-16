package com.lyh.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyh.community.dao.IModuleDao;
import com.lyh.community.dao.IUserDao;
import com.lyh.community.entity.Module;
import com.lyh.community.entity.Resp;
import com.lyh.community.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class ModuleService implements IModuleService {
    @Autowired
    private IModuleDao iModuleDao;
    @Autowired
    private IUserDao iUserDao;

//    @Override
//    public Resp<Module> createModule(String mname, String intro, String email, String password) {
//        if(iUserDao.selectCountByEmailPassword(email, DigestUtils.md5DigestAsHex(password.getBytes()))==0){
//            return Resp.fail("499","身份验证错误！");
//        }
//        else if(iModuleDao.selectCountByMname(mname)==1){
//            return Resp.fail("410","板块已存在！");
//        }
//        else{
//            iModuleDao.insertByMnameIntroUid(mname,intro,iUserDao.selectUidByEmail(email));
//            return Resp.success(null);
//        }
//    }
//
//    @Override
//    public Resp<Module> deleteModule(String mname, String email, String password) {
//        if(iUserDao.selectCountByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
//            return Resp.fail("499","身份验证错误！");
//        }
//        else if(iModuleDao.selectCountByMname(mname)==0){
//            return Resp.fail("411","板块不存在！");
//        }
//        else if(iModuleDao.selectUidByMname(mname)!=iUserDao.selectUidByEmail(email)){
//            return Resp.fail("488","用户无权限！");
//        }
//        else{
//            iModuleDao.deleteByMname(mname);
//            return Resp.success(null);
//        }
//    }

    @Override
    public Resp<PageInfo<Module>> showModuleByUid(String email, String password, int pageNum, int pageSize) {
        if(iUserDao.selectCountByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }
        else{
            int uid = iUserDao.selectUidByEmail(email);
            PageHelper.startPage(pageNum,pageSize);
            PageInfo<Module> pageInfo = new PageInfo<Module>(iModuleDao.selectAllByUid(uid));
            return Resp.success(pageInfo);
        }
    }

    @Override
    public Resp<PageInfo<Module>> showModule(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Module> pageInfo = new PageInfo<Module>(iModuleDao.selectAll());
        return Resp.success(pageInfo);
    }
}
