package com.shyam_learning.basic_auth.service.impl;


import com.shyam_learning.basic_auth.dto.UserDto;
import com.shyam_learning.basic_auth.entity.User;
import com.shyam_learning.basic_auth.repository.UserRepository;
import com.shyam_learning.basic_auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserDto> fetchAll() {
        return userRepository.findAll()
                .stream().map(User::toDto).toList();
    }

    @Override
    public Optional<UserDto> findById(Long userId) {
        return userRepository.findById(userId).map(User::toDto);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    public UserDto save(UserDto userDto) {
        userDto.setCreatedAt(LocalDateTime.now());
        return userRepository.save(userDto.toEntity()).toDto();
    }
}
