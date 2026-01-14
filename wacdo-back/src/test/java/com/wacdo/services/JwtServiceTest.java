package com.wacdo.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.wacdo.controllers.WacdoApplication;
import com.wacdo.controllers.services.JwtService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest(
        classes = WacdoApplication.class,
        properties = {
                "jwt.security.secret=test-secret",
                "jwt.security.expiration=60000"
        }
)
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void shouldGenerateAndVerifyToken() {
        UserDetails user = User.withUsername("john")
                .password("pwd")
                .authorities("ADMIN")
                .build();

        String token = jwtService.generateToken(user);
        DecodedJWT decoded = jwtService.verify(token);

        Assertions.assertThat(decoded.getSubject()).isEqualTo("john");
        Assertions.assertThat(decoded.getClaim("admin").asBoolean()).isTrue();
    }
}