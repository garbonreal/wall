package com.lyh.community.dao;

import com.lyh.community.entity.Module;
import com.lyh.community.entity.Topic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IModuleDao {
    int selectCountByMname(@Param("mname") String mname);

    void insertByMnameIntroUid(@Param("mname") String mname, @Param("intro") String intro, @Param("uid") int uid);

    void deleteByMname(@Param("mname") String mname);

    int selectUidByMname(@Param("mname") String mname);

    List<Module> selectAllByUid(@Param("uid") int uid);

    List<Module> selectAll();

    int selectMidByMname(@Param("mname") String mname);

    int selectUidByMid(@Param("mid") int mid);
}
