package com.lyh.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyh.community.dao.*;
import com.lyh.community.entity.Comment;
import com.lyh.community.entity.Module;
import com.lyh.community.entity.Resp;
import com.lyh.community.entity.Topic;
import com.lyh.community.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class CommentService implements ICommentService {
    @Autowired
    private ICommentDao iCommentDao;
    @Autowired
    private IUserDao iUserDao;
    @Autowired
    private ITopicDao iTopicDao;
    @Autowired
    private IModuleDao iModuleDao;
    @Autowired
    private ILikeCommentDao iLikeCommentDao;

    @Override
    public Resp<Comment> createComment(String content, String email, String password, String ttime, int ccanonymous, String ctime, String mname) {
        if(iUserDao.selectCountByEmailPassword(email, DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }
        else if(iTopicDao.selectCountByTtimeMid(ttime,iModuleDao.selectMidByMname(mname))==0){
            return Resp.fail("421","话题不存在！");
        }
        else{
//            int M = 1;
//            String maxnum = iCommentDao.selectMaxNumByTid(iTopicDao.selectTidByTnameMid(ttime,iModuleDao.selectMidByMname(mname)));
//            if(maxnum!=null){
//                M = Integer.parseInt(maxnum)+1;
//            }
            iCommentDao.insertByContentUidTidNum(content,iTopicDao.selectTidByTnameMid(ttime,iModuleDao.selectMidByMname(mname)),ccanonymous,ctime);
            return Resp.success(null);
        }
    }

    @Override
    public Resp<Comment> deleteComment(String email, String password, String ttime, String mname, String ctime) {
        if(iUserDao.selectCountByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }
        else if(iCommentDao.selectCountByTidCtime(iTopicDao.selectTidByTnameMid(ttime,iModuleDao.selectMidByMname(mname)),ctime)==0){
            return Resp.fail("431","评论不存在！");
        }
        // 不再区分 uid
//        else if(iCommentDao.selectUidByTidCtime(iTopicDao.selectTidByTnameMid(ttime,iModuleDao.selectMidByMname(mname)),ctime)!=iUserDao.selectUidByEmail(email)&&iTopicDao.selectUidByTtimeMid(tname,iModuleDao.selectMidByMname(mname))!=iUserDao.selectUidByEmail(email)){
//            return Resp.fail("488","用户无权限！");
//        }
        else{
            iCommentDao.deleteByTidCtime(iTopicDao.selectTidByTnameMid(ttime,iModuleDao.selectMidByMname(mname)),ctime);
            return Resp.success(null);
        }
    }

    @Override
    public Resp<PageInfo<Comment>> showCommentByTopic(String ttime, int pageNum, int pageSize, String mname) {
        if(iTopicDao.selectCountByTtimeMid(ttime,iModuleDao.selectMidByMname(mname))==0){
            return Resp.fail("421","话题不存在！");
        }
        else{
            int tid = iTopicDao.selectTidByTnameMid(ttime,iModuleDao.selectMidByMname(mname));
            PageHelper.startPage(pageNum,pageSize);
            List<Comment> CommentList = iCommentDao.selectAllByTid(tid);
            for (Comment comment : CommentList) {
                if(comment.getCanonymous()==0){
                    comment.setUname("匿名");
                }
            }
            PageInfo<Comment> pageInfo = new PageInfo<Comment>(CommentList);
            System.out.print(pageInfo);
            return Resp.success(pageInfo);
        }
    }

    @Override
    public Resp<Comment> likeComment(String email, String password, String tname, int num, String mname) {
        if(iUserDao.selectCountByEmailPassword(email,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            return Resp.fail("499","身份验证错误！");
        }
//        else if(iCommentDao.selectCountByTidCtime(iTopicDao.selectTidByTnameMid(tname,iModuleDao.selectMidByMname(mname)),num)==0){
//            return Resp.fail("431","评论不存在！");
//        }
        else if(iLikeCommentDao.selectCountByUidCid(iUserDao.selectUidByEmail(email),iCommentDao.selectCidByTidNum(iTopicDao.selectTidByTnameMid(tname,iModuleDao.selectMidByMname(mname)),num))==1){
            return Resp.fail("432","已赞！");
        }
        else{
            iLikeCommentDao.insertByUidCid(iUserDao.selectUidByEmail(email),iCommentDao.selectCidByTidNum(iTopicDao.selectTidByTnameMid(tname,iModuleDao.selectMidByMname(mname)),num));
            iCommentDao.updateAddLikeByTidNum(iTopicDao.selectTidByTnameMid(tname,iModuleDao.selectMidByMname(mname)),num);
            return Resp.success(null);
        }
    }
}
