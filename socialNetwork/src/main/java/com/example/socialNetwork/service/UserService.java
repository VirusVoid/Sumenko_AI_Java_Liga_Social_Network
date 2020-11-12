package com.example.socialNetwork.service;

import com.example.socialNetwork.domain.Friends;
import com.example.socialNetwork.domain.Users;
import com.example.socialNetwork.domain.enums.StatusEnum;
import com.example.socialNetwork.dto.UserByListDto;
import com.example.socialNetwork.dto.UserPersonalPageDto;
import com.example.socialNetwork.dto.UserRegistryDto;
import com.example.socialNetwork.repository.FriendsRepository;
import com.example.socialNetwork.repository.UserRepository;
import com.example.socialNetwork.service.filters.UserFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Валидация данных пользователя и отправка запросов в БД
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final FriendsRepository friendsRepository;

    /**
     * Проверка данных пользователя и внесение их в БД
     *
     * @param userRegistryDto регистрационные данные пользователя
     * @return идентификатор пользователя
     */
    @Transactional
    public Integer createUser(UserRegistryDto userRegistryDto) {
        Users user = convertUserRegisterDtoToUser(userRegistryDto, new Users());
        user = userRepository.save(user);
        return user.getId();
    }

    /**
     * Обновление данных пользователя и внесение их в БД
     *
     * @param userPersonalPageDto данные пользователя
     */
    @Transactional
    public Integer updateUser(Integer id, UserPersonalPageDto userPersonalPageDto) {
        Users foundUser = Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Users user = convertUserPersonalPageDtoToUser(userPersonalPageDto, foundUser);
        user = userRepository.save(user);
        return user.getId();
    }

    /**
     * Получение данных пользователя
     *
     * @param id идентификатор пользователя
     * @return данные пользователя
     */
    public UserPersonalPageDto getUser(Integer id) {
        return userRepository.findById(id)
                .map(this::convertToUserPersonalPageDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Удаление пользователя
     *
     * @param id идентификатор пользователя
     */
    @Transactional
    public Integer deleteUser(Integer id) {
        if (getFriendList(id) != null) {
            List<Integer> toDelete = new ArrayList<>();
            List<Friends> list1 = friendsRepository.findByFirstFriend(id);
            List<Friends> list2 = friendsRepository.findBySecondFriend(id);
            for (Friends f : list1) {
                toDelete.add(f.getId());
            }
            for (Friends f : list2) {
                toDelete.add(f.getId());
            }
            for (Integer i : toDelete) {
                friendsRepository.deleteById(i);
            }
        }
        Users user = Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);

        return user.getId();
    }

    /**
     * Получение списка друзей пользователя
     *
     * @param id идентификатор пользователя
     * @return список друзей
     */
    public List<UserByListDto> getFriendList(Integer id) {
        Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Integer> friends = new ArrayList<>();
        List<Friends> list1 = friendsRepository.findByFirstFriend(id);
        List<Friends> list2 = friendsRepository.findBySecondFriend(id);
        for (Friends f : list1) {
            if (f.getStatus() == StatusEnum.ACCEPTED) {
                friends.add(f.getSecondFriend());
            }
        }
        for (Friends f1 : list2) {
            if (f1.getStatus() == StatusEnum.ACCEPTED) {
                friends.add(f1.getFirstFriend());
            }
        }
        List<UserByListDto> friendList = new ArrayList<>();
        for (Integer friendId : friends) {
            userRepository.findById(friendId)
                    .map(this::convertToUserByListDto)
                    .ifPresent(friendList::add);
        }
        return friendList;
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

    /**
     * Преобразование DTO-сущности в {@link Users}
     *
     * @param dto данные о пользователе
     * @return пользователь
     */
    private Users convertUserRegisterDtoToUser(UserRegistryDto dto, Users user) {
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());

        return user;
    }

    /**
     * Преобразование DTO-сущности в {@link Users}
     *
     * @param dto данные о пользователе
     * @return пользователь
     */
    private Users convertUserPersonalPageDtoToUser(UserPersonalPageDto dto, Users user) {
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setAge(dto.getAge());
        user.setGender(dto.getGender());
        user.setInterests(dto.getInterests());
        user.setCity(dto.getCity());

        return user;
    }

    /**
     * Преобразование сущности {@link Users} в DTO
     *
     * @param user пользователь
     * @return DTO
     */
    private UserPersonalPageDto convertToUserPersonalPageDto(Users user) {
        UserPersonalPageDto dto = new UserPersonalPageDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setAge(user.getAge());
        dto.setGender(user.getGender());
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

        return dto;
    }
}
