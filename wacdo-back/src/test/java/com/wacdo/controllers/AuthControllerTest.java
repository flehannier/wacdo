package com.wacdo.controllers;

import com.jayway.jsonpath.JsonPath;
import com.wacdo.controllers.controllers.AuthController;
import com.wacdo.controllers.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Slf4j
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    private final String email = "user@test.com";
    private final String password = "Password123";

    @BeforeEach
    void setup() {
        // UserDetails factice
        UserDetails userDetails = User.withUsername(email)
                .password(password)
                .authorities("ADMIN")
                .build();

        // Mock de l'AuthenticationManager
        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                ));

        // Mock du JWT généré
        when(jwtService.generateToken(any(UserDetails.class)))
                .thenReturn("mocked-jwt-token");
    }

    @Test
    void loginShouldReturnJwt() throws Exception {
        String requestBody = String.format("{\"email\":\"%s\", \"motDePasse\":\"%s\"}", email, password);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
            //    .andExpect(status().isOk())
           //     .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"))
                .andReturn();


        // Extraire et logger la valeur de "token"
        String responseBody = result.getResponse().getContentAsString();
        log.info("responseBody : {}", responseBody);
    }
}
