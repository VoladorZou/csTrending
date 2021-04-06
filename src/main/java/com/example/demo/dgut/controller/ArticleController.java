package com.example.demo.dgut.controller;

import com.example.demo.dgut.dao.ArticleDao;
import com.example.demo.dgut.model.Article;
import com.example.demo.dgut.model.User;
import com.example.demo.dgut.service.ArticleService;
import com.example.demo.dgut.service.UserService;
import com.example.demo.dgut.util.JsonDataResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@Api(tags = "文章相关操作接口")
@Slf4j
@RequestMapping("/article")
@CrossOrigin()
public class ArticleController {

    @Autowired
    public ArticleService articleService;
    @Autowired
    private ArticleDao articleDao;
    // 文件日期
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
    // 判断当前操作结果
    private boolean operationResult;

    // 保存文章
    @ApiOperation(value = "保存文章", notes = "用户可以将编辑好的文章保存到服务器")
    @PostMapping("/saveArticle")
    public JsonDataResult saveArticle(@RequestBody Article article){
        log.info("文章相关信息：[{}]",article.toString());
        // 插入数据库
        try {
            operationResult = articleService.saveArticle(article);
            return JsonDataResult.buildSuccess(operationResult);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 保存文章的其它信息
    @ApiOperation(value = "保存文章的其它信息", notes = "用户可以将编辑好的文章的其它信息保存到服务器")
    @PostMapping("/updateArticleTag")
    public JsonDataResult updateArticleTag(@RequestBody Article article){
        log.info("文章相关信息：[{}]",article.toString());
        // 插入数据库
        try {
            return JsonDataResult.buildSuccess(articleDao.updateArticle(article));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 管理员审核文章
    @ApiOperation(value = "管理员审核文章", notes = "")
    @GetMapping("/permitArticle")
    public JsonDataResult permitArticle(int articleid, boolean permit){
        log.info("文章ID信息：[{}]",articleid);
        log.info("文章ID信息：[{}]",permit);
        // 插入数据库
        try {
            return JsonDataResult.buildSuccess(articleDao.permitArticle(articleid, permit));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 修改文章
    @ApiOperation(value = "保存文章", notes = "用户可以将修改好的文章保存到服务器")
    @PostMapping("/updateArticle")
    public JsonDataResult updateArticle(@RequestBody Article article){
        log.info("文章相关信息：[{}]",article.toString());
        // 插入数据库
        try {
            operationResult = articleDao.updateMarkdown(article.getArticleid(),article.getMarkdown());
            return JsonDataResult.buildSuccess(operationResult);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 根据文章ID获取文章信息
    @ApiOperation(value = "根据文章ID获取文章信息", notes = "用户可以根据articleid获取文章信息")
    @GetMapping("/getArticleByArticleId")
    public JsonDataResult getArticleByArticleId(int articleid){
        log.info("发起请求的文章ID：[{}]",articleid);
        try {
            Article article = articleService.getArticleByArticleId(articleid);
            return JsonDataResult.buildSuccess(article);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 根据文章标签获取文章信息
    @ApiOperation(value = "根据文章标签获取文章信息", notes = "用户可以根据tag获取文章信息")
    @GetMapping("/getArticleByTag")
    public JsonDataResult getArticleByTag(String tag, int pagenum, int pagesize){
        log.info("发起请求的文章标签：[{}]",tag);
        try {
            PageHelper.startPage(pagenum, pagesize);
            List<Article> articleList = articleDao.getArticleByTag(tag);
            PageInfo<Article> pageInfo = new PageInfo<Article>(articleList);
            return JsonDataResult.buildSuccess(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 根据用户ID获取文章信息
    @ApiOperation(value = "根据用户ID获取文章信息", notes = "用户可以根据userid获取文章信息")
    @GetMapping("/getArticleByUserId")
    public JsonDataResult getArticleByUserId(int userid, int pagenum, int pagesize){
        log.info("发起请求的用户id：[{}]",userid);
        try {
            PageHelper.startPage(pagenum, pagesize);
            List<Article> articleList = articleService.getArticleByUserId(userid);
            PageInfo<Article> pageInfo = new PageInfo<Article>(articleList);
            return JsonDataResult.buildSuccess(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 分页获取文章列表
    @ApiOperation(value = "分页获取文章列表",notes = "分页查询数据")
    @GetMapping("/getArticleListByPage")
    public JsonDataResult getArticleListByPage(int pagenum, int pagesize, String query){
        log.info("发起请求的pagenum：[{}]",pagenum);
        log.info("发起请求的pagesize：[{}]",pagesize);
        log.info("发起请求的pagesize：[{}]",query);
        if(query.length()==0) {
            PageHelper.startPage(pagenum, pagesize);
            List<Article> articleList = articleService.getArticleList();
            PageInfo<Article> pageInfo = new PageInfo<Article>(articleList);
            return JsonDataResult.buildSuccess(pageInfo);
        }else {
            PageHelper.startPage(pagenum, pagesize);
            List<Article> articleList = articleDao.getArticleListByArticle(query);
            PageInfo<Article> pageInfo = new PageInfo<Article>(articleList);
            return JsonDataResult.buildSuccess(pageInfo);
        }
    }

    @ApiOperation(value = "文章上传图片",notes = "")
    @PostMapping("/upLoadArticleCover")
    public JsonDataResult upLoadArticleCover(@RequestParam(value = "file") MultipartFile uploadFile, HttpServletRequest req){
//            String realPath = req.getSession().getServletContext().getRealPath("/uploadFile/");
        String realPath = "E:/csTrending/uploadFile/";
            System.out.println(realPath);
            String format = sdf.format(new Date());
            File folder = new File(realPath + format);
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
            String oldName = uploadFile.getOriginalFilename();
            String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
            try {
                uploadFile.transferTo(new File(folder, newName));
                String filePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/uploadFile/" + format + newName;
                return JsonDataResult.buildSuccess(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return JsonDataResult.buildError("上传失败！");
    }
}
