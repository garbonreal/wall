package com.lyh.community.dao;

import com.lyh.community.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentDao {
    void insertByContentUidTidNum(@Param("content") String content, @Param("tid") int tid, @Param("canonymous") int canonymous, @Param("ctime") String ctime);

    String selectMaxNumByTid(@Param("tid") int tid);

    void deleteByTidCtime(@Param("tid") int tid, @Param("ctime") String ctime);

//    int selectUidByTidCtime(@Param("tid") int tid, @Param("ctime") int ctime);

    List<Comment> selectAllByTid(@Param("tid") int tid);

    void updateAddLikeByTidNum(@Param("tid") int tid, @Param("num") int num);

    int selectCidByTidNum(@Param("tid") int tid, @Param("num") int num);

    int selectCountByTidCtime(@Param("tid") int tid, @Param("ctime") String ctime);
}
