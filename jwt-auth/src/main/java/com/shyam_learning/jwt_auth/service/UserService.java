package com.shyam_learning.jwt_auth.service;

import com.shyam_learning.jwt_auth.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<UserDto> fetchAll();

    public Optional<UserDto> findById(Long userId);

    public UserDto save(UserDto userDto);
}
