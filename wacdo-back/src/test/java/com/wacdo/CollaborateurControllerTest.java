package com.wacdo;

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
class CollaborateurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username="test", roles={"ADMIN"})
    void shouldReturn200() throws Exception {
        mockMvc.perform(get("/collaborateur"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="test", roles={"USER"})
    void shouldReturn401() throws Exception {
        mockMvc.perform(get("/collaborateur"))
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }
}
