package com.kevin.emazon_cart.infraestructure.config;

import com.kevin.emazon_cart.domain.api.ICartServicePort;
import com.kevin.emazon_cart.domain.spi.ICartPersistentPort;
import com.kevin.emazon_cart.domain.spi.externalservices.IConnectionStockPort;
import com.kevin.emazon_cart.domain.spi.externalservices.IConnectionTransactionPort;
import com.kevin.emazon_cart.domain.usecase.CartUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBeanClass {
    @Bean
    ICartServicePort getServicePort(ICartPersistentPort persistentPort, IConnectionStockPort stockPort, IConnectionTransactionPort transactionPort){
        return new CartUseCase(persistentPort, stockPort, transactionPort);
    }

}
