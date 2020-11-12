package com.example.socialNetwork.service;

import com.example.socialNetwork.domain.Friends;
import com.example.socialNetwork.domain.Users;
import com.example.socialNetwork.domain.enums.StatusEnum;
import com.example.socialNetwork.dto.UserByListDto;
import com.example.socialNetwork.dto.UserPersonalPageDto;
import com.example.socialNetwork.dto.UserRegistryDto;
import com.example.socialNetwork.repository.FriendsRepository;
import com.example.socialNetwork.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FriendsRepository friendsRepository;

    @Test
    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, friendsRepository);
    }

    @Test
    void testCreateUser() {
        UserRegistryDto userdto = new UserRegistryDto();
        userdto.setName("Ann");
        userdto.setSurname("Malen");
        userdto.setId(1);
        Users user = mock(Users.class);
        when(userRepository.save(any(Users.class))).thenReturn(user);
        Integer id = userService.createUser(userdto);
        assertNotNull(id);
        assertEquals(id, user.getId());
        verify(userRepository, times(1)).save(any(Users.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUser() {
        UserPersonalPageDto userdto = new UserPersonalPageDto();
        userdto.setName("Ann");
        userdto.setSurname("Male");
        Users user1 = mock(Users.class);
        user1.setName("Ann");
        user1.setSurname("Malen");
        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        when(userRepository.save(any(Users.class))).thenReturn(user1);
        Integer id = userService.updateUser(user1.getId(), userdto);
        assertNotNull(id);
        assertEquals(id, user1.getId());
        verify(userRepository, times(1)).save(any(Users.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getUser() {
        Users user1 = mock(Users.class);
        user1.setName("Ann");
        user1.setSurname("Malen");
        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        UserPersonalPageDto page = userService.getUser(user1.getId());
        assertNotNull(page);
        verify(userRepository, times(1)).findById(user1.getId());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void deleteUser() {
        Users user1 = mock(Users.class);
        user1.setName("Ann");
        user1.setSurname("Malen");
        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        Integer id = userService.deleteUser(user1.getId());
        assertNotNull(id);
        assertEquals(id, user1.getId());
        verify(userRepository, times(1)).delete(user1);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getFriendList() {
        Users user1 = new Users();
        user1.setId(1);
        user1.setName("Ann");
        user1.setSurname("Malen");

        Users friend1 = new Users();
        friend1.setId(2);
        friend1.setName("Kate");
        friend1.setSurname("Mills");

        Users friend2 = new Users();
        friend2.setId(3);
        friend2.setName("Jane");
        friend2.setSurname("Port");

        Friends first = new Friends();
        first.setId(1);
        first.setFirstFriend(1);
        first.setSecondFriend(2);
        first.setStatus(StatusEnum.ACCEPTED);
        Friends second = new Friends();
        second.setId(2);
        second.setFirstFriend(1);
        second.setSecondFriend(3);
        second.setStatus(StatusEnum.ACCEPTED);

        UserByListDto first1 = new UserByListDto();
        first1.setId(friend1.getId());
        first1.setUserName(String.format("%s %s", friend1.getName(), friend1.getSurname()));

        UserByListDto second1 = new UserByListDto();
        second1.setId(friend2.getId());
        second1.setUserName(String.format("%s %s", friend2.getName(), friend2.getSurname()));

        List<Friends> friendList = new ArrayList<>();
        List<Friends> friendList2 = new ArrayList<>();
        friendList.add(first);
        friendList.add(second);

        List<Integer> friends = new ArrayList<>();
        friends.add(2);
        friends.add(3);

        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        when(friendsRepository.findByFirstFriend(user1.getId())).thenReturn(friendList);
        when(friendsRepository.findBySecondFriend(user1.getId())).thenReturn(friendList2);
        when(userRepository.findById(friends.get(0))).thenReturn(java.util.Optional.of(friend1));
        when(userRepository.findById(friends.get(1))).thenReturn(java.util.Optional.of(friend2));

        List<UserByListDto> list = userService.getFriendList(user1.getId());
        assertNotNull(list);
        assertEquals(list.get(0), first1);
        assertEquals(list.get(1), second1);
        verify(userRepository, times(1)).findById(user1.getId());
        verify(friendsRepository, times(1)).findByFirstFriend(user1.getId());
        verify(friendsRepository, times(1)).findBySecondFriend(user1.getId());
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(friendsRepository);
    }

}