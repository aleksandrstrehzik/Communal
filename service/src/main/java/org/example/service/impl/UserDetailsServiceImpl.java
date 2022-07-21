package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.UserRepository;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user " + username + "!");
        }

        return new UserDetailsImpl(user);
    }

    public UserDto getUserByName(String userName) {
        return userMapper.toDto(userRepository.findByUserName(userName));
    }
}
