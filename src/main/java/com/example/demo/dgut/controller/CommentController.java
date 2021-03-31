package com.example.demo.dgut.controller;

import com.example.demo.dgut.dao.CommentDao;
import com.example.demo.dgut.dao.UserDao;
import com.example.demo.dgut.model.Article;
import com.example.demo.dgut.model.Comment;
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
@Api(tags = "文章评论接口")
@Slf4j
@RequestMapping("/comment")
@CrossOrigin()
public class CommentController {

    @Autowired
    private CommentDao commentDao;

    // 保存评论
    @ApiOperation(value = "保存评论", notes = "")
    @PostMapping("/saveComment")
    public JsonDataResult saveComment(@RequestBody Comment comment){
//        log.info("文章相关信息：[{}]",comment.toString());
        // 插入数据库
        try {
            return JsonDataResult.buildSuccess(commentDao.saveComment(comment));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 查询评论
    @ApiOperation(value = "查询评论", notes = "")
    @GetMapping("/getComment")
    public JsonDataResult getComment(int pagenum, int pagesize, int articleid){
        log.info("文章相关信息：[{}]",articleid);
        // 插入数据库
        try {
            PageHelper.startPage(pagenum, pagesize);
            List<Comment> commentList= commentDao.getComment(articleid);
            PageInfo<Comment> pageInfo = new PageInfo<Comment>(commentList);
            return JsonDataResult.buildSuccess(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 分页查询评论
    @ApiOperation(value = "分页获取文章列表",notes = "分页查询数据")
    @GetMapping("/getCommentListByPage")
    public JsonDataResult getCommentListByPage(int pagenum, int pagesize, String query){
        log.info("发起请求的pagenum：[{}]",pagenum);
        log.info("发起请求的pagesize：[{}]",pagesize);
        log.info("发起请求的pagesize：[{}]",query);
        if(query.length()==0) {
            PageHelper.startPage(pagenum, pagesize);
            List<Comment> commentList = commentDao.getCommentList();
            PageInfo<Comment> pageInfo = new PageInfo<Comment>(commentList);
            return JsonDataResult.buildSuccess(pageInfo);
        }else {
            PageHelper.startPage(pagenum, pagesize);
            List<Comment> commentList = commentDao.getCommentListByQuery(query);
            PageInfo<Comment> pageInfo = new PageInfo<Comment>(commentList);
            return JsonDataResult.buildSuccess(pageInfo);
        }
    }


}
