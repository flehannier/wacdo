package com.wacdo;

import com.wacdo.controllers.AuthController;
import com.wacdo.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(AuthController.class)
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
        this.setup();

        String requestBody = String.format("{\"email\":\"%s\", \"motDePasse\":\"%s\"}", email, password);
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accesToken").value("mocked-jwt-token"))
                .andDo(response -> {
                    String body = response.getResponse().getContentAsString();
                    int status = response.getResponse().getStatus();
                    log.info("HTTP STATUS = {}", status);
                    log.info("RESPONSE BODY = {}", body);
                });
    }

    @Test
    void loginShouldReturnUnauthorized() throws Exception {
        // Mock de l'AuthenticationManager
        when(authenticationManager.authenticate(any()))
                .thenThrow(BadCredentialsException.class);

        String requestBody = String.format("{\"email\":\"%s\", \"motDePasse\":\"%s\"}", email, password);
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Email ou mot de passe incorrect."))
                .andDo(response -> {
                    String body = response.getResponse().getContentAsString();
                    int status = response.getResponse().getStatus();
                    log.info("HTTP STATUS = {}", status);
                    log.info("RESPONSE BODY = {}", body);
                });
    }
}
