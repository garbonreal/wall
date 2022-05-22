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
    private ICollectTopicDao iCollectTopicDao;

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
        else if(iTopicDao.selectUidByTtimeMid(ttime,iModuleDao.selectMidByMname(mname))!=iUserDao.selectUidByEmail(email)){
            return Resp.fail("488","用户无权限！");
        }
        else{
            iTopicDao.deleteByTtimeMid(ttime,iModuleDao.selectMidByMname(mname));
            return Resp.success(null);
        }
    }

    @Override
    public Resp<Topic> deleteFavoriteTopic(String ttime, String email, String password, String mname) {
        if(iUserDao.selectCountByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }
        else if(iCollectTopicDao.selectCountByUidTid(iUserDao.selectUidByEmail(email),
                iTopicDao.selectTidByTtimeMid(ttime,iModuleDao.selectMidByMname(mname)))==0){
            return Resp.fail("421","话题不存在！");
        }
        else{
            iCollectTopicDao.deleteByUidTid(iUserDao.selectUidByEmail(email),
                    iTopicDao.selectTidByTtimeMid(ttime,iModuleDao.selectMidByMname(mname)));
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
            List<Topic> favoriteTopic=iCollectTopicDao.selectAllByUid(uid);
            System.out.println("uid="+uid);
            for(Topic t:favoriteTopic){
                System.out.println(t.getMname()+";"+t.getTtime()+";"+t.getTcontent()+";"+t.getUname());
                if(t.getTanonymous()==0) t.setUname("匿名");
            }
            PageInfo<Topic> pageInfo = new PageInfo<Topic>(favoriteTopic);
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
                //添加clike属性
                int clike=iLikeTopicDao.selectCountByTid(topic.getTid());
                // System.out.println("tid="+topic.getTid()+"; clike="+clike);
                topic.setClike(clike);
            }
            PageInfo<Topic> pageInfo = new PageInfo<Topic>(TopicList);

            System.out.print(pageInfo);

            return Resp.success(pageInfo);
        }
    }

    @Override
    public Resp<Topic> likeTopic(String email, String password, String ttime, String mname) {
        if(iUserDao.selectCountByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }
        else if(iTopicDao.selectCountByTtimeMid(ttime,iModuleDao.selectMidByMname(mname))==0){
            return Resp.fail("421","话题不存在！");
        }
        else if(iLikeTopicDao.selectCountByUidTid(iUserDao.selectUidByEmail(email),iTopicDao.selectTidByTtimeMid(ttime,iModuleDao.selectMidByMname(mname)))==1){
            return Resp.fail("422","已赞！");
        }
        else{
            iLikeTopicDao.insertByUidTid(iUserDao.selectUidByEmail(email),iTopicDao.selectTidByTtimeMid(ttime,iModuleDao.selectMidByMname(mname)));
            // iTopicDao.updateAddLikeByTtimeMid(ttime,iModuleDao.selectMidByMname(mname));
            return Resp.success(null);
        }
    }
    
    @Override
    public Resp<Topic> collectTopic(String email, String password, String ttime, String mname) {
        if(iUserDao.selectCountByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }
        else if(iTopicDao.selectCountByTtimeMid(ttime,iModuleDao.selectMidByMname(mname))==0){
            return Resp.fail("421","话题不存在！");
        }
        else if(iCollectTopicDao.selectCountByUidTid(iUserDao.selectUidByEmail(email),iTopicDao.selectTidByTtimeMid(ttime,iModuleDao.selectMidByMname(mname)))==1){
            // System.out.println("uid="+iUserDao.selectUidByEmail(email)+" tid="+iTopicDao.selectTidByTtimeMid(ttime,iModuleDao.selectMidByMname(mname)));
            return Resp.fail("423","已收藏！");
        }
        else{
            iCollectTopicDao.insertByUidTid(iUserDao.selectUidByEmail(email),iTopicDao.selectTidByTtimeMid(ttime,iModuleDao.selectMidByMname(mname)));
            return Resp.success(null);
        }
    }
}
