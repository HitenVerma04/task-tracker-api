package com.hitenverma.tasktracker.dto;

import com.hitenverma.tasktracker.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    @Builder.Default
    private String tokenType = "Bearer";
    private String username;
    private Role role;
    private long expiresInMs;
}
