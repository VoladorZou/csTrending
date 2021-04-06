package com.example.demo.dgut.controller;

import com.example.demo.dgut.dao.FellowDao;
import com.example.demo.dgut.dao.UserDao;
import com.example.demo.dgut.model.Article;
import com.example.demo.dgut.model.Fellow;
import com.example.demo.dgut.model.User;
import com.example.demo.dgut.util.JsonDataResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "关注用户接口")
@Slf4j
@RequestMapping("/fellow")
@CrossOrigin()
public class FellowController {

    @Autowired
    private FellowDao fellowDao;
    // 判断当前操作结果
    private boolean operationResult;

    // 添加关注
    @ApiOperation(value = "添加关注",notes = "登录用户可对别的创作者点击关注")
    @GetMapping("/fellowing")
    public JsonDataResult fellowing(int userid, int uploaderid) {
        log.info("用户信息：[{}]", userid);
        log.info("创作用户信息：[{}]", uploaderid);
        try {
            operationResult = fellowDao.fellowing(userid,uploaderid);
            return JsonDataResult.buildSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 取消关注
    @ApiOperation(value = "取消关注",notes = "取消关注")
    @GetMapping("/unfellow")
    public JsonDataResult unfellow(int userid, int uploaderid) {
        log.info("用户信息：[{}]", userid);
        log.info("创作用户信息：[{}]", uploaderid);
        try {
            operationResult = fellowDao.unfellow(userid,uploaderid);
            return JsonDataResult.buildSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 查询关注状态
    @ApiOperation(value = "查询关注状态",notes = "")
    @GetMapping("/fellowState")
    public JsonDataResult fellowState(int userid, int uploaderid) {
        log.info("fellowState用户信息：[{}]", userid);
        log.info("fellowState创作用户信息：[{}]", uploaderid);
        try {
            operationResult = fellowDao.fellowState(userid,uploaderid);
            return JsonDataResult.buildSuccess(operationResult);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 分页获取关注用户列表
    @ApiOperation(value = "分页获取关注用户列表",notes = "分页查询数据")
    @GetMapping("/getFellowList")
    public JsonDataResult getFellowList(int pagenum, int pagesize, int userid){
        log.info("发起请求的pagenum：[{}]",pagenum);
        log.info("发起请求的pagesize：[{}]",pagesize);
        log.info("发起请求的userid：[{}]",userid);

            PageHelper.startPage(pagenum, pagesize);
            List<Fellow> fellowList = fellowDao.getFellowList(userid);
            PageInfo<Fellow> pageInfo = new PageInfo<Fellow>(fellowList);
            return JsonDataResult.buildSuccess(pageInfo);

    }

}
