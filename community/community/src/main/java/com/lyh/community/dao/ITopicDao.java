package com.lyh.community.dao;

import com.lyh.community.entity.Topic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ITopicDao {
    int selectCountByTtimeMid(@Param("ttime") String ttime, @Param("mid") int mid);

    void deleteByTtimeMid(@Param("ttime") String ttime, @Param("mid") int mid);

    int selectUidByTtimeMid(@Param("ttime") String ttime, @Param("mid") int mid);

    List<Topic> selectAllByUid(@Param("uid") int uid);

    List<Topic> selectAllByMid(@Param("mid") int mid);

    List<Topic> selectAll();
    
    int selectTidByTtimeMid(@Param("ttime") String ttime, @Param("mid") int mid);

    void insertByTnameIntroMidUid(String intro, int tanonymous, @Param("ttime") String ttime, @Param("mid") int selectMidByMname, @Param("uid") int selectUidByEmail);
}
