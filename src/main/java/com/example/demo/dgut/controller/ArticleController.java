package com.example.demo.dgut.controller;

import com.example.demo.dgut.model.Article;
import com.example.demo.dgut.model.User;
import com.example.demo.dgut.service.ArticleService;
import com.example.demo.dgut.service.UserService;
import com.example.demo.dgut.util.JsonDataResult;
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

    // 获取文章
    @ApiOperation(value = "获取文章信息", notes = "用户可以根据userid获取文章信息")
    @GetMapping("/getArticleByUserId")
    public JsonDataResult getArticleByUserId(int userid){
        log.info("请求用户的id：[{}]",userid);
        try {
            List<Article> articleList;
            articleList = articleService.getArticleByUserId(userid);
            return JsonDataResult.buildSuccess(articleList);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // md格式文章上传
    @PostMapping("/saveArticleMd")
    public String uploadProfilePhoto(@RequestParam MultipartFile uploadFile, HttpServletRequest req){
        String realPath = "E:/csTrending/uploadFile/";
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
                return filePath;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }
}
