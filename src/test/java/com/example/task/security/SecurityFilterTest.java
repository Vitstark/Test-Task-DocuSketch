package com.example.task.security;

import com.example.task.TaskApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TaskApplication.class)
@AutoConfigureMockMvc
public class SecurityFilterTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void anonymousUserLoginIsOk() throws Exception {
        mvc.perform(get("/auth/login"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void anonymousUserRegisterIsOk() throws Exception {
        mvc.perform(get("/auth/registration"))
                .andExpect(status().isOk());
    }
}
