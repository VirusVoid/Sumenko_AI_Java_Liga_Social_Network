package com.example.socialNetwork.controller;

import com.example.socialNetwork.domain.Users;
import com.example.socialNetwork.dto.UserPersonalPageDto;
import com.example.socialNetwork.dto.UserRegistryDto;
import com.example.socialNetwork.repository.UserRepository;
import com.example.socialNetwork.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest
class UserControllerUnitTest {
    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;

    @Test
    void registerUser() {
        UserRegistryDto user = new UserRegistryDto();
        user.setName("Mary");
        user.setSurname("Jane");
        userController.registerUser(user);
        verify(this.userService, times(1)).createUser(any(UserRegistryDto.class));
        when(userService.createUser(user)).thenReturn(user.getId());
    }

    @Test
    void getUser() {
        Users testUser = generateTestUser();
        userController.getUser(testUser.getId());
        System.out.println(testUser.getId());
        verify(this.userService, times(1)).getUser(testUser.getId());
    }

    @Test
    void updateUser() {
        Users testUser = generateTestUser();
        UserPersonalPageDto page = new UserPersonalPageDto();
        page.setCity("Moscow");
        userController.updateUser(testUser.getId(), page);
        verify(this.userService, times(1)).updateUser(testUser.getId(), page);
    }

    @Test
    void deleteUser() {
        Users testUser = generateTestUser();
        userController.deleteUser(testUser.getId());
        verify(this.userService, times(1)).deleteUser(testUser.getId());
    }

    private Users generateTestUser() {
        Users user = new Users();
        user.setName("Mary");
        user.setSurname("Jane");
        userRepository.save(user);
        return user;
    }
}