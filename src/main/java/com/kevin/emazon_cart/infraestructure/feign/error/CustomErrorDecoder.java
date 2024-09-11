package com.kevin.emazon_cart.infraestructure.feign.error;

import com.kevin.emazon_cart.infraestructure.exception.FeignRequestException;
import com.kevin.emazon_cart.infraestructure.exception.FeignServerException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    public static final String FEIGN_SERVER_EXCEPTION_MESSAGE = "Error dentro en el servidor del microservicio foraneo: ";
    public static final String FEIGN_REQUEST_EXCEPTION_MESSAGE = "Error en la petición: ";
    private final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() >= 400 && response.status() < 500) {
            return new FeignRequestException(FEIGN_REQUEST_EXCEPTION_MESSAGE + response.reason());
        } else if (response.status() >= 500) {
            return new FeignServerException(FEIGN_SERVER_EXCEPTION_MESSAGE + response.reason());
        }
        return defaultDecoder.decode(methodKey, response);
    }
}
