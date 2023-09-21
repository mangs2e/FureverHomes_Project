package com.example.fureverhomes_project.config;

import com.example.fureverhomes_project.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/fureverhomes/**")
                .excludePathPatterns("/fureverhomes/main", "/fureverhomes/signin/**", "/fureverhomes/signup/**" ,
                        "/fureverhomes/signin.**","/fureverhomes/signup.**");
    }

}
