package com.example.demo.dgut.controller;

import com.example.demo.dgut.dao.MessageDao;
import com.example.demo.dgut.model.Comment;
import com.example.demo.dgut.model.Message;
import com.example.demo.dgut.util.JsonDataResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "网站留言接口")
@Slf4j
@RequestMapping("/message")
@CrossOrigin()
public class MessageController {

    @Autowired
    private MessageDao messageDao;

    // 留言
    @ApiOperation(value = "留言",notes = "用户、游客给开发者使用反馈或建议")
    @PostMapping("/sendMessage")
    public JsonDataResult sendMessage(@RequestBody Message message) {
        log.info("用户信息：[{}]", message.toString());
        try {
            return JsonDataResult.buildSuccess(messageDao.sendMessage(message));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 分页获取留言数据
    @ApiOperation(value = "分页获取文章列表",notes = "分页查询数据")
    @GetMapping("/getMessageListByPage")
    public JsonDataResult getCommentListByPage(int pagenum, int pagesize, String query){
        log.info("发起请求的pagenum：[{}]",pagenum);
        log.info("发起请求的pagesize：[{}]",pagesize);
        log.info("发起请求的pagesize：[{}]",query);
        if(query.length()==0) {
            PageHelper.startPage(pagenum, pagesize);
            List<Message> messageList = messageDao.getMessageList();
            PageInfo<Message> pageInfo = new PageInfo<Message>(messageList);
            return JsonDataResult.buildSuccess(pageInfo);
        }else {
            PageHelper.startPage(pagenum, pagesize);
            List<Message> messageList = messageDao.getMessageListByQuery(query);
            PageInfo<Message> pageInfo = new PageInfo<Message>(messageList);
            return JsonDataResult.buildSuccess(pageInfo);
        }
    }
}
