package com.wacdo.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.mapping.Collection;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String username;
    private List roles;
    private String accessToken;
}
