package com.DailyLife;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class staticResourceConfig implements WebMvcConfigurer {

    @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("user/assets/**").addResourceLocations("classpath:/static/assets/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("assets/**").addResourceLocations("classpath:/static/assets/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("user/images/**").addResourceLocations("classpath:/static/images/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("board/images/**").addResourceLocations("classpath:/static/images/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("board/assets/**").addResourceLocations("classpath:/static/assets/").setCachePeriod(60 * 60 * 24 * 365);

    }
}
