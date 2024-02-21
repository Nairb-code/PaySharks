package com.bripay.oauthserver.api;

import com.bripay.commonsservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "localhost:8082")
public interface IUserFeignClient {
    /** Obtener Usuario por Username. **/
    @GetMapping("/api/v1/users/username/{username}")
    public ResponseEntity<UserDto> findByUsername(@PathVariable String username);
}
