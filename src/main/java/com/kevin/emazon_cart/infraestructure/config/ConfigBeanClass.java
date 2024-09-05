package com.kevin.emazon_cart.infraestructure.config;

import com.kevin.emazon_cart.domain.api.ICartServicePort;
import com.kevin.emazon_cart.domain.spi.ICartPersistentPort;
import com.kevin.emazon_cart.domain.usecase.CartUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBeanClass {
    @Bean
    ICartServicePort getServicePort(ICartPersistentPort persistentPort){
        return new CartUseCase(persistentPort);
    }

}
