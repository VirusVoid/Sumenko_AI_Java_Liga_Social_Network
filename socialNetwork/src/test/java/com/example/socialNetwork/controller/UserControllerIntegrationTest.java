package com.example.socialNetwork.controller;

import com.example.socialNetwork.domain.Users;
import com.example.socialNetwork.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerUser() throws Exception {
        Users user = createTestUser();
        ResultActions result = mockMvc.perform(
                post("/user")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        String cont = result.andReturn().getResponse().getContentAsString();
        userRepository.deleteById(Integer.valueOf(cont));
    }

    @Test
    void getUser() throws Exception {
        Users user = createTestUser();
        userRepository.save(user);
        mockMvc.perform(
                get("/user/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value("Jane"))
                .andExpect(jsonPath("$.surname").value("Lean"))
                .andDo(print());
        userRepository.delete(user);
    }

    @Test
    void updateUser() throws Exception {
        Users user = createTestUser();
        userRepository.save(user);
        Users user1 = user;
        user1.setSurname("Lake");
        mockMvc.perform(
                put("/user/{id}", user.getId())
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(user.getId().toString()))
                .andDo(print());
        userRepository.delete(user);
    }

    @Test
    void deleteUser() throws Exception {
        Users user = createTestUser();
        userRepository.save(user);
        mockMvc.perform(
                delete("/user/{id}", user.getId()))
                .andExpect(status().isOk());
    }

    private Users createTestUser() {
        Users user = new Users();
        user.setName("Jane");
        user.setSurname("Lean");

        return user;
    }
}