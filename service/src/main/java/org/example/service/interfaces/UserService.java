package org.example.service.interfaces;

import org.example.dto.UserDto;

public interface UserService {
    UserDto getUserByName(String userName);
}
