package org.example.service;

import org.example.dto.UserDto;

public interface UserService {
    UserDto getUserByName(String userName);
}
