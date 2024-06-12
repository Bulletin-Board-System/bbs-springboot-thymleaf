package org.bbsgroup.bbs.config;

import org.bbsgroup.bbs.Interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PageConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        // 登陆拦截
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/app/register")
                .excludePathPatterns("/app/login")
                .addPathPatterns("/app/logout")
                .addPathPatterns("/app/addPost")
                .addPathPatterns("/app/editPost/**")
                .addPathPatterns("/app/detail/**")
                .addPathPatterns("/app/center")
                .addPathPatterns("/app/userSet");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/app/login").setViewName("/user/login");
        registry.addViewController("/app/register").setViewName("/user/reg");
    }
}
