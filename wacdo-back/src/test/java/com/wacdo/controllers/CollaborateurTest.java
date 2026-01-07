package com.wacdo.controllers;

import com.wacdo.controllers.repositories.CollaborateurRepository;
import com.wacdo.controllers.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CollaborateurTest {

    @Autowired
    private CollaborateurRepository collaborateurRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn403_withoutToken() throws Exception {
        mockMvc.perform(get("/api/collaborateur"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturn200_withToken() throws Exception {
        String fakeJwt = "Bearer eyJhbGciOiJIUzI1NiJ9.fake.payload";

        mockMvc.perform(
                        get("/api/collaborateur")
                                .header("Authorization", fakeJwt)
                )
                .andExpect(status().isOk());
    }
}
