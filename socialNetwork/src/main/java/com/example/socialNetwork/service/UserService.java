package com.example.socialNetwork.service;

import com.example.socialNetwork.domain.Friends;
import com.example.socialNetwork.domain.PersonalPages;
import com.example.socialNetwork.domain.Users;
import com.example.socialNetwork.domain.enums.StatusEnum;
import com.example.socialNetwork.dto.PersonalPageByListDto;
import com.example.socialNetwork.dto.UserByListDto;
import com.example.socialNetwork.dto.UserRegistryDto;
import com.example.socialNetwork.repository.FriendsRepository;
import com.example.socialNetwork.repository.UserRepository;
import com.example.socialNetwork.service.filters.UserFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final FriendsRepository friendsRepository;

    /**
     * Проверка данных пользователя и внесение их в БД
     *
     * @param userRegistryDto регистрационные данные пользователя
     * @return
     */
    @Transactional
    public ResponseEntity registerUser(UserRegistryDto userRegistryDto) {
        Users users = convertUserRegisterDtoToUser(userRegistryDto, new Users());
        System.out.println(users.getId());
        users = userRepository.save(users);
        System.out.println(users.getId());
        return new ResponseEntity<>(users.getId(), HttpStatus.OK);
    }

    public ResponseEntity getFriendList(Integer id) {
        Users entity = Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Integer> friends = new ArrayList<>();
        List<Friends> list1 = friendsRepository.findByFirstFriend(id);
        System.out.println(list1.get(0));
        List<Friends> list2 = friendsRepository.findBySecondFriend(id);
        for (Friends f : list1) {
            if (f.getStatus() == StatusEnum.ACCEPTED) {
                friends.add(f.getSecondFriend());
            }
        }
        System.out.println(friends.get(0));
        for (Friends f1 : list2) {
            if (f1.getStatus() == StatusEnum.ACCEPTED) {
                friends.add(f1.getFirstFriend());
            }
        }
        List<UserByListDto> friendList = new ArrayList<>();
        for (Integer friendId : friends) {
            userRepository.findById(friendId)
                    .map(this::addUserToUserByListDto)
                    .ifPresent(friendList::add);
        }
        return new ResponseEntity<>(friendList, HttpStatus.OK);
    }

    /**
     * Поиск пользователя по идентификатору
     *
     * @param id идентификатор пользователя
     * @return
     */
    public UserRegistryDto findUserById(Integer id) {
        return userRepository.findById(id)
                .map(this::convertToUserRegistryDto)
                .orElse(null);
    }

    /**
     * Получение всех пользователей с учетом фильтра
     *
     * @param filter   фильтр
     * @param pageable пагинация
     * @return страница с пользователями
     */
    public Page<UserByListDto> findAll(UserFilter filter, Pageable pageable) {
        return userRepository
                .findAll(filter.toSpecification(), pageable)
                .map(this::convertToUserByListDto);
    }

    private Users convertUserRegisterDtoToUser(UserRegistryDto dto, Users entity) {
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setAge(dto.getAge());
        entity.setGender(dto.getGender());
        entity.setInterests(dto.getInterests());
        entity.setCity(dto.getCity());

        return entity;
    }

    private UserRegistryDto convertToUserRegistryDto(Users user) {
        UserRegistryDto dto = new UserRegistryDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setAge(user.getAge());
        dto.setGender(user.getGender());
        dto.setInterests(user.getInterests());
        dto.setCity(user.getCity());
        return dto;
    }

    private UserByListDto addUserToUserByListDto(Users user) {
        UserByListDto dto = new UserByListDto();
        dto.setId(user.getId());
        dto.setUserName(String.format("%s %s", user.getName(), user.getSurname()));
        dto.setInterests(user.getInterests());
        dto.setCity(user.getCity());
        return dto;
    }

    /**
     * Преобразование сущности {@link Users} в DTO
     *
     * @param user пользователь
     * @return DTO
     */
    private UserByListDto convertToUserByListDto(Users user) {
        UserByListDto dto = new UserByListDto();
        dto.setId(user.getId());
        dto.setUserName(String.format("%s %s", user.getName(), user.getSurname()));
        dto.setInterests(user.getInterests());
        dto.setCity(user.getCity());

        return dto;
    }

}
