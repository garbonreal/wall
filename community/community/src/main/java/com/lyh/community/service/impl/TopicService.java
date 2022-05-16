package com.lyh.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyh.community.dao.*;
import com.lyh.community.entity.Module;
import com.lyh.community.entity.Resp;
import com.lyh.community.entity.Topic;
import com.lyh.community.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class TopicService implements ITopicService {
    @Autowired
    private ITopicDao iTopicDao;
    @Autowired
    private IUserDao iUserDao;
    @Autowired
    private IModuleDao iModuleDao;
    @Autowired
    private ILikeTopicDao iLikeTopicDao;
    @Autowired
    private IFavoriteDao iFavoriteDao;

    @Override
    public Resp<Topic> createTopic(String intro, String email, String password, int tanonymous, String ttime, String mname) {
        if(iUserDao.selectCountByEmailPassword(email, DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }
        else{
            iTopicDao.insertByTnameIntroMidUid(intro,tanonymous,ttime,iModuleDao.selectMidByMname(mname),iUserDao.selectUidByEmail(email));
            System.out.print(ttime);
            return Resp.success(null);
        }
    }

    @Override
    public Resp<Topic> deleteTopic(String ttime, String email, String password, String mname) {
        if(iUserDao.selectCountByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }
        else if(iTopicDao.selectCountByTtimeMid(ttime, iModuleDao.selectMidByMname(mname))==0){
            return Resp.fail("421","话题不存在！");
        }
        else if(iTopicDao.selectUidByTtimeMid(ttime,iModuleDao.selectMidByMname(mname))!=iUserDao.selectUidByEmail(email)&&iModuleDao.selectUidByMid(iModuleDao.selectMidByMname(mname))!=iUserDao.selectUidByEmail(email)){
            return Resp.fail("488","用户无权限！");
        }
        else{
            iTopicDao.deleteByTtimeMid(ttime,iModuleDao.selectMidByMname(mname));
            return Resp.success(null);
        }
    }

    @Override
    public Resp<PageInfo<Topic>> showTopicByUid(String email, String password, int pageNum, int pageSize) {
        if(iUserDao.selectCountByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }
        else{
            int uid = iUserDao.selectUidByEmail(email);
            PageHelper.startPage(pageNum,pageSize);
            PageInfo<Topic> pageInfo = new PageInfo<Topic>(iTopicDao.selectAllByUid(uid));
            return Resp.success(pageInfo);
        }
    }

    @Override
    public Resp<PageInfo<Topic>> showFavoriteByUid(String email, String password, int pageNum, int pageSize) {
        if(iUserDao.selectCountByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }
        else{
            int uid = iUserDao.selectUidByEmail(email);
            PageHelper.startPage(pageNum,pageSize);
            PageInfo<Topic> pageInfo = new PageInfo<Topic>(iFavoriteDao.selectAllByUid(uid));
            return Resp.success(pageInfo);
        }
    }

    @Override
    public Resp<PageInfo<Topic>> showTopicByModule(String mname, int pageNum, int pageSize) {
        if(mname==null){
            PageHelper.startPage(pageNum,pageSize);
            PageInfo<Topic> pageInfo = new PageInfo<Topic>(iTopicDao.selectAll());
            return Resp.success(pageInfo);
        }
        else if(iModuleDao.selectCountByMname(mname)==0){
            return Resp.fail("411","板块不存在！");
        }
        else{
            int mid = iModuleDao.selectMidByMname(mname);
            PageHelper.startPage(pageNum,pageSize);
            List<Topic> TopicList = iTopicDao.selectAllByMid(mid);
            for (Topic topic : TopicList) {
                if(topic.getTanonymous()==0){
                    topic.setUname("匿名");
                }
            }
            PageInfo<Topic> pageInfo = new PageInfo<Topic>(TopicList);

            System.out.print(pageInfo);

            return Resp.success(pageInfo);
        }
    }

    @Override
    public Resp<Topic> likeTopic(String email, String password, String tname, String mname) {
        if(iUserDao.selectCountByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }
        else if(iTopicDao.selectCountByTtimeMid(tname,iModuleDao.selectMidByMname(mname))==0){
            return Resp.fail("421","话题不存在！");
        }
        else if(iLikeTopicDao.selectCountByUidTid(iUserDao.selectUidByEmail(email),iTopicDao.selectTidByTnameMid(tname,iModuleDao.selectMidByMname(mname)))==1){
            return Resp.fail("422","已赞！");
        }
        else{
            iLikeTopicDao.insertByUidTid(iUserDao.selectUidByEmail(email),iTopicDao.selectTidByTnameMid(tname,iModuleDao.selectMidByMname(mname)));
            iTopicDao.updateAddLikeByTnameMid(tname,iModuleDao.selectMidByMname(mname));
            return Resp.success(null);
        }
    }

    @Override
    public Resp<Topic> favorite(String email, String password, String tname, String mname) {
        if(iUserDao.selectCountByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }
        else if(iTopicDao.selectCountByTtimeMid(tname,iModuleDao.selectMidByMname(mname))==0){
            return Resp.fail("421","话题不存在！");
        }
        else if(iFavoriteDao.selectCountByUidTid(iUserDao.selectUidByEmail(email),iTopicDao.selectTidByTnameMid(tname,iModuleDao.selectMidByMname(mname)))==1){
            return Resp.fail("423","已收藏！");
        }
        else{
            iFavoriteDao.insertByUidTid(iUserDao.selectUidByEmail(email),iTopicDao.selectTidByTnameMid(tname,iModuleDao.selectMidByMname(mname)));
            return Resp.success(null);
        }
    }
}
