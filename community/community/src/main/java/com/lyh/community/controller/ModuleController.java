package com.lyh.community.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyh.community.entity.Module;
import com.lyh.community.entity.Resp;
import com.lyh.community.entity.User;
import com.lyh.community.service.IModuleService;
import com.lyh.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/module")
public class ModuleController {

    @Autowired
    private IModuleService iModuleService;

//    @RequestMapping(value = "/createModule",method = RequestMethod.POST)
//    private Resp<Module> createModule(String mname, String intro, String email, String password){
//        if(email.length()>40||password.length()>40||intro.length()>100||mname.length()>40){
//            return Resp.fail("777","参数错误！");
//        }
//        return iModuleService.createModule(mname, intro, email, password);
//    }

//    @RequestMapping(value = "/deleteModule",method = RequestMethod.POST)
//    private Resp<Module> deleteModule(String mname, String email, String password){
//        if(email.length()>40||password.length()>40||mname.length()>40){
//            return Resp.fail("777","参数错误！");
//        }
//        return iModuleService.deleteModule(mname, email, password);
//    }

    @RequestMapping(value = "/showModuleByUid",method = RequestMethod.POST)
    private Resp<PageInfo<Module>> showModuleByUid(String email, String password, int pageNum, int pageSize){
        if(email.length()>40||password.length()>40){
            return Resp.fail("777","参数错误！");
        }
        return iModuleService.showModuleByUid(email, password, pageNum, pageSize);
    }

    @RequestMapping(value = "/showModule",method = RequestMethod.POST)
    private Resp<PageInfo<Module>> showModule(int pageNum, int pageSize){
        return iModuleService.showModule(pageNum,pageSize);
    }
}
