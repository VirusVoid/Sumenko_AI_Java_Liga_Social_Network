package com.example.socialNetwork.controller;

import com.example.socialNetwork.dto.UserByListDto;
import com.example.socialNetwork.dto.UserPersonalPageDto;
import com.example.socialNetwork.dto.UserRegistryDto;
import com.example.socialNetwork.service.UserService;
import com.example.socialNetwork.service.filters.UserFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Обработка HTTP-запросов
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Зарегистрировать нового пользователя
     *
     * @param userRegistryDto регистрационные данные пользователя в виде DTO
     * @return идентификатор пользователя
     */
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegistryDto userRegistryDto) {
        Integer id = userService.createUser(userRegistryDto);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * Получить пользователя по идентификатору
     *
     * @param id идентификатор пользователя
     * @return DTO пользователя
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        UserPersonalPageDto user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Обновить данные пользователя
     *
     * @param personalPage обновленные данные пользователя в виде DTO
     * @return идентификатор пользователя
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody @Valid UserPersonalPageDto personalPage) {
        Integer userId = userService.updateUser(id, personalPage);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    /**
     * Удалить пользователя из базы данных
     *
     * @param id идентификатор пользователя
     * @return статус Http-запроса
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Получить список друзей пользователя
     *
     * @param id идентификатор пользователя
     * @return список друзей
     */
    @GetMapping("/friends/{id}")
    public ResponseEntity<?> getFriendList(@PathVariable Integer id) {
        List<UserByListDto> list = userService.getFriendList(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Получить страницу с отфильтрованными пользователями
     *
     * @param filter   фильтр
     * @param pageable настройки пагинации
     * @return страница с пользователями
     */
    @GetMapping
    public Page<UserByListDto> findAll(UserFilter filter, Pageable pageable) {
        return userService.findAll(filter, pageable);
    }
}
