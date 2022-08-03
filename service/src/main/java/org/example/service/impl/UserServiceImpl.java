package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.UserRepository;
import org.example.dto.UserDto;
import org.example.mapper.UserMapper;
import org.example.service.interfaces.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getUserByName(String userName) {
        return userMapper.toDto(userRepository.findByUserName(userName));
    }
}
