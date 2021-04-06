package com.example.demo.dgut.controller;

import com.example.demo.dgut.dao.FellowDao;
import com.example.demo.dgut.dao.LikeDao;
import com.example.demo.dgut.dao.ThumpDao;
import com.example.demo.dgut.util.JsonDataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "点赞文章接口")
@Slf4j
@RequestMapping("/like")
@CrossOrigin()
public class LikeController {

    @Autowired
    private ThumpDao thumpDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 判断Rdis中是否存在当前key
    private boolean hasKey;

    // 点赞
    @ApiOperation(value = "添加关注",notes = "登录用户可对别的创作者点击关注")
    @GetMapping("/likeing")
    public JsonDataResult likeing(int userid, int articleid) {
        log.info("用户信息：[{}]", userid);
        log.info("创作用户信息：[{}]", articleid);
        try {
            return JsonDataResult.buildSuccess(thumpDao.like(userid,articleid));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 取消点赞
    @ApiOperation(value = "取消关注",notes = "取消关注")
    @GetMapping("/unlike")
    public JsonDataResult unlike(int userid, int articleid) {
        log.info("用户信息：[{}]", userid);
        log.info("创作用户信息：[{}]", articleid);
        try {
            return JsonDataResult.buildSuccess(thumpDao.unlike(userid,articleid));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("失败");
        }
    }

    // 查询点赞状态
    @ApiOperation(value = "查询关注状态",notes = "")
    @GetMapping("/likeState")
    public JsonDataResult likeState(HttpServletRequest request, int articleid) {
        String tokenInfo = request.getHeader("Authorization");
        log.info("tokenInfo：[{}]", tokenInfo);
        log.info("articleid：[{}]", articleid);
        //查询缓存中是否存在
            hasKey = stringRedisTemplate.hasKey(tokenInfo);
            if (hasKey) {
                try {
                    int userId = Integer.parseInt(stringRedisTemplate.opsForValue().get(tokenInfo));
                    log.info("fellowState用户信息：[{}]", userId);
                    return JsonDataResult.buildSuccess(thumpDao.likeState(userId, articleid));
                } catch (Exception e) {
                    e.printStackTrace();
                    return JsonDataResult.buildError("失败");
                }
            } else {
                return JsonDataResult.buildError("用户不存在或登录过期");
            }
    }
}
