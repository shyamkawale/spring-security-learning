package com.shyam_learning.form_based_auth.dto;

import com.shyam_learning.form_based_auth.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class UserDto {
    private Long userId;
    private String username;
    private String password;
    private String roles;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    public UserDto() {
    }

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.createdAt = user.getCreatedAt();
        this.lastLogin = user.getLastLogin();
    }

    public User toEntity() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles == null ? "USER_ROLE" : roles);
        user.setCreatedAt(createdAt);
        user.setLastLogin(lastLogin);
        return user;
    }

}
