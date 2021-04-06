package com.example.demo.dgut.interceptors;

import com.example.demo.dgut.util.JsonDataResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
public class RedisInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //拦截器取到请求先进行判断，如果是OPTIONS请求(嗅探访问)，则放行
        if("OPTIONS".equals(request.getMethod().toUpperCase())) {
//            System.out.println("Method:OPTIONS");
            return true;
        }
        // 获取请求头中令牌
        String token = request.getHeader("Authorization");
        // token为 null 则用户未登录
        if (null == token){
            String json = new ObjectMapper().writeValueAsString(JsonDataResult.buildError("用户未登录"));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json);
            return false;
        }
        // token为 null 则用户不存在
        String redisToken = stringRedisTemplate.opsForValue().get(token);
        if(redisToken == null){
            String json = new ObjectMapper().writeValueAsString(JsonDataResult.buildError("用户不存在"));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json);
            return false;
        }
        // expire 小于等于0 则登录过期
        Long expire = stringRedisTemplate.getExpire(token);
        if(expire <= 0){
            String json = new ObjectMapper().writeValueAsString(JsonDataResult.buildError("登录已过期"));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json);
            return false;
        }
        // 前面校验都通过则放行
        return true;
    }
}
