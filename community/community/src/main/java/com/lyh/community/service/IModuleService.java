package com.lyh.community.service;

import com.github.pagehelper.PageInfo;
import com.lyh.community.entity.Resp;
import com.lyh.community.entity.Module;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IModuleService {

    Resp<PageInfo<Module>> showModuleByUid(String email, String password, int pageNum, int pageSize);

    Resp<PageInfo<Module>> showModule(int pageNum, int pageSize);
}
