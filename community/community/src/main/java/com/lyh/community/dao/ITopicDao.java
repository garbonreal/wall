package com.lyh.community.dao;

import com.lyh.community.entity.Topic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ITopicDao {
    int selectCountByTnameMid(@Param("tname") String tname, @Param("mid") int mid);

    void insertByTnameIntroMidUid(@Param("tname") String tname, @Param("intro") String intro, @Param("mid") int mid, @Param("uid") int uid);

    void deleteByTnameMid(@Param("tname") String tname, @Param("mid") int mid);

    int selectUidByTnameMid(@Param("tname") String tname, @Param("mid") int mid);

    List<Topic> selectAllByUid(@Param("uid") int uid);

    List<Topic> selectAllByMid(@Param("mid") int mid);

    List<Topic> selectAll();

    void updateAddLikeByTnameMid(@Param("tname") String tname, @Param("mid") int mid);

    int selectTidByTnameMid(@Param("tname") String tname, @Param("mid") int mid);

    void insertByTnameIntroMidUid(String intro, int tanonymous, String date, @Param("mid") int selectMidByMname, @Param("uid") int selectUidByEmail);
}
