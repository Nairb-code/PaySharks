package com.bripay.oauthserver.service;

import com.bripay.commonsservice.dto.UserDto;

public interface IUserService {
    UserDto findByUsername(String username);
}
