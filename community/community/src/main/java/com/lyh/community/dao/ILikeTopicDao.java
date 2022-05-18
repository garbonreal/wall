package com.lyh.community.dao;

import com.lyh.community.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILikeTopicDao {
    int selectCountByUidTid(@Param("uid") int uid, @Param("tid") int tid);

    void deleteByUidTid(@Param("uid") int uid, @Param("tid") int tid);

    void insertByUidTid(@Param("uid") int uid, @Param("tid") int tid);
    
    int selectCountByTid(int tid);
}
