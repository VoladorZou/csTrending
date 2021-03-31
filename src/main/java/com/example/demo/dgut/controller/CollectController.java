package com.example.demo.dgut.controller;

import com.example.demo.dgut.dao.CollectionDao;
import com.example.demo.dgut.dao.FellowDao;
import com.example.demo.dgut.model.Collection;
import com.example.demo.dgut.model.Fellow;
import com.example.demo.dgut.util.JsonDataResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "收藏文章接口")
@Slf4j
@RequestMapping("/collect")
@CrossOrigin()
public class CollectController {

    @Autowired
    private CollectionDao collectionDao;
    // 判断当前操作结果
    private boolean operationResult;

    // 添加关注
    @ApiOperation(value = "添加关注",notes = "登录用户可对别的创作者点击关注")
    @GetMapping("/collecting")
    public JsonDataResult collecting(int userid, int articleid) {
        log.info("用户信息：[{}]", userid);
        log.info("文章信息ID：[{}]", articleid);
        try {
            operationResult = collectionDao.collecting(userid,articleid);
            return JsonDataResult.buildSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 取消关注
    @ApiOperation(value = "取消关注",notes = "")
    @GetMapping("/uncollect")
    public JsonDataResult uncollect(int userid, int articleid) {
        log.info("用户信息：[{}]", userid);
        log.info("文章信息ID：[{}]", articleid);
        try {
            operationResult = collectionDao.uncollect(userid,articleid);
            return JsonDataResult.buildSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 查询关注状态
    @ApiOperation(value = "查询关注状态",notes = "")
    @GetMapping("/collectState")
    public JsonDataResult collectState(int userid, int articleid) {
        log.info("collectState用户信息：[{}]", userid);
        log.info("collectState文章信息ID：[{}]", articleid);
        try {
            operationResult = collectionDao.collectState(userid,articleid);
            return JsonDataResult.buildSuccess(operationResult);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 分页获取收藏文章列表
    @ApiOperation(value = "分页获取收藏文章列表",notes = "分页查询数据")
    @GetMapping("/getCollectionList")
    public JsonDataResult getCollectionList(int pagenum, int pagesize, int userid){
        log.info("发起请求的pagenum：[{}]",pagenum);
        log.info("发起请求的pagesize：[{}]",pagesize);
        log.info("发起请求的userid：[{}]",userid);

        PageHelper.startPage(pagenum, pagesize);
        List<Collection> collectionList = collectionDao.getCollectionList(userid);
        PageInfo<Collection> pageInfo = new PageInfo<Collection>(collectionList);
        return JsonDataResult.buildSuccess(pageInfo);

    }
}
