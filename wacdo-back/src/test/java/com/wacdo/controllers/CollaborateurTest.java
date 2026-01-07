package com.wacdo.controllers;

import com.wacdo.controllers.repositories.CollaborateurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CollaborateurTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username="test", roles={"ADMIN"})
    void shouldReturn200_withMockUser() throws Exception {
        mockMvc.perform(get("/collaborateur"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="test", roles={"USER"})
    void shouldReturn400_withMockUser() throws Exception {
        mockMvc.perform(get("/collaborateur"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }
}
