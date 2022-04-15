package com.DailyLife;

import com.DailyLife.web.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/user/addUser", "/user/findUser", "/user/main" ,
                        "/css/**", "/*.ico", "/error" , "/user/assets/**" , "/assets/**" , "/user/findById" , "/user/findByIdToPw" , "/mail/sendEmailtoFind"
                        , "/user/images/**" ,"/images/**" , "/mail/emailAuthorCheck/**", "/mail/EmailAuthor/**" , "/user/test" , "/mail/AuthNumSend");
    }
}
