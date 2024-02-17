package com.example.globalStudents.global.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://www.globalstudents.shop", "https://feature-102-sign-up-api--ephemeral-belekoy-506c71.netlify.app/sign-up", "localhost:3000", "localhost:8080")
                .allowedOriginPatterns("https://www.globalstudents.shop", "https://feature-102-sign-up-api--ephemeral-belekoy-506c71.netlify.app/sign-up", "localhost:3000", "localhost:8080")
                .allowedHeaders("*")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name()
                );

    }
}
