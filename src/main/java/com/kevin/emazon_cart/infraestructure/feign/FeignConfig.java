package com.kevin.emazon_cart.infraestructure.feign;

import com.kevin.emazon_cart.infraestructure.feign.error.CustomErrorDecoder;
import com.kevin.emazon_cart.infraestructure.feign.interceptor.JwtRequestInterceptor;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel(){return  Logger.Level.FULL;}


    @Bean
    public RequestInterceptor requestInterceptor(){
        return new JwtRequestInterceptor();
    }

    @Bean
    public ErrorDecoder errorDecoder(){
        return new CustomErrorDecoder();
    }

}
