package com.lyh.community.service;

import com.github.pagehelper.PageInfo;
import com.lyh.community.entity.Module;
import com.lyh.community.entity.Resp;
import com.lyh.community.entity.Topic;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public interface ITopicService {
    Resp<Topic> createTopic(String intro, String email, String password, int tanonymous, String date, String mname);

    Resp<Topic> deleteTopic(String tname, String email, String password, String mname);

    Resp<PageInfo<Topic>> showTopicByUid(String email, String password, int pageNum, int pageSize);

    Resp<PageInfo<Topic>> showFavoriteByUid(String email, String password, int pageNum, int pageSize);

    Resp<PageInfo<Topic>> showTopicByModule(String mname, int pageNum, int pageSize);//如果是NULL返回全部topic

    Resp<Topic> likeTopic(String email, String password, String tname, String mname);//需要检验数据库liketopic表

    Resp<Topic> favorite(String email, String password, String tname, String mname);//收藏
}
