package com.lyh.community.dao;

import com.lyh.community.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentDao {
    void insertByContentUidTidNum(@Param("content") String content, @Param("uid") int uid, @Param("tid") int tid, @Param("num") int num);

    String selectMaxNumByTid(@Param("tid") int tid);

    void deleteByTidNum(@Param("tid") int tid, @Param("num") int num);

    int selectUidByTidNum(@Param("tid") int tid, @Param("num") int num);

    List<Comment> selectAllByTidOrderByNum(@Param("tid") int tid);

    void updateAddLikeByTidNum(@Param("tid") int tid, @Param("num") int num);

    int selectCidByTidNum(@Param("tid") int tid, @Param("num") int num);

    int selectCountByTidNum(@Param("tid") int tid, @Param("num") int num);
}
