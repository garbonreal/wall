package com.lyh.community.dao;

import com.lyh.community.entity.Topic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICollectTopicDao {
    int selectCountByUidTid(@Param("uid")int uid,@Param("tid")int tid);
    void insertByUidTid(@Param("uid")int uid,@Param("tid")int tid);
    void deleteByUidTid(@Param("uid")int uid,@Param("tid")int tid);
    List<Topic> selectAllByUid(int uid);
}
