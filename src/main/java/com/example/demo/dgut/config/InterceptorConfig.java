package com.example.demo.dgut.config;


import com.example.demo.dgut.interceptors.RedisInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    // 提前实例化拦截器对象，防止后面数据成员 StringRedisTemplate注入不成功
    @Bean
    public RedisInterceptor getRedisInterceptor(){
        return new RedisInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getRedisInterceptor())
                .addPathPatterns("/user/getUserInfo")        // 需要token验证的其他接口
                .addPathPatterns("/user/setUserInfo")
                .addPathPatterns("/user/logout")
                .addPathPatterns("/user/getUserList")
                .excludePathPatterns("/article/getArticleListByPage")
                .excludePathPatterns("/user/sendEmail") // 放行发送邮箱接口
                .excludePathPatterns("/user/sendVerifyCode") // 放行发送验证码接口
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/adminLogin");  //所有用户都放行登录接口
    }

    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 注意文件路径不能有空格，如 E: //csTrending 是无法识别的
        registry.addResourceHandler("/uploadFile/**").addResourceLocations("file:E:/csTrending/uploadFile/");
        super.addResourceHandlers(registry);
    }
}
