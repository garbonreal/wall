package com.lyh.community.dao;

import com.lyh.community.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILikeCommentDao {
    int selectCountByUidCid(@Param("uid") int uid, @Param("cid") int cid);

    //添加用户点赞
    void insertByUidCid(@Param("uid") int uid, @Param("cid") int cid);
    
    //取消用户点赞
    void deleteByUidCid(@Param("uid") int uid, @Param("cid") int cid);
    
    int selectCountByCid(int cid);
}
