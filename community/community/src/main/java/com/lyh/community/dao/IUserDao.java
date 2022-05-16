package com.lyh.community.dao;

import com.lyh.community.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao {
    int selectCountByEmailPassword(@Param("email") String email, @Param("password") String password);

    User selectAllByEmailPassword(@Param("email") String email, @Param("password") String password);

    int selectCountByEmail(@Param("email") String email);

    void insertByEmailPasswordUname(@Param("email") String email, @Param("password") String password, @Param("uname") String uname);

    void updateAllExpectEmailPassword(@Param("email") String email, @Param("uname") String uname, @Param("sex") byte sex, @Param("age") byte age, @Param("motto") String motto);

    void updatePassword(@Param("email") String email, @Param("password") String password);

    void updateEmail(@Param("newemail") String newemail, @Param("oldemail") String oldemail);

    int selectUidByEmail(@Param("email") String email);
}
