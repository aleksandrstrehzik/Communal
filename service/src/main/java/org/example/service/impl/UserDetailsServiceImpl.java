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

import static org.example.service.impl.MockUtils.COULD_NOT_FIND_USER;
import static org.example.service.impl.MockUtils.STRING;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(COULD_NOT_FIND_USER + username + STRING);
        }

        return new UserDetailsImpl(user);
    }

}
