package com.bripay.paymentservice.api;

import com.bripay.commonsservice.exception.ResourceNotFoundException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            // Personalizar el mensaje de error y lanzar una excepción específica
            String[] split = response.request().url().split("/");

            return new ResourceNotFoundException("The account " + split[split.length - 1] + " is not registered.");
        }

        // En otros casos, devolver una excepción genérica de Feign
        return FeignException.errorStatus(methodKey, response);
    }
}
