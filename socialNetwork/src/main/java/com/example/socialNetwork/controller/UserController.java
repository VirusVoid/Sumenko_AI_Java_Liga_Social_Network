package com.example.socialNetwork.controller;

import com.example.socialNetwork.dto.UserByListDto;
import com.example.socialNetwork.dto.UserRegistryDto;
import com.example.socialNetwork.service.UserService;
import com.example.socialNetwork.service.filters.UserFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Зарегистрировать нового пользователя
     *
     * @param userRegistryDto регистрационные данные пользователя в виде DTO
     * @return идентификатор пользователя
     */
    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid UserRegistryDto userRegistryDto){
      //  log.info("Регистрация нового пользователя user={}", userRegisterDto.toString());
        return userService.registerUser(userRegistryDto);
    }

    /**
     * Получить список друзей пользователя
     * @param id идентификатор пользователя
     * @return список друзей
     */
    @GetMapping("/friends/{id}")
    public ResponseEntity<?> getFriendList(@PathVariable Integer id){
        //  log.info("Регистрация нового пользователя user={}", userRegisterDto.toString());
        return userService.getFriendList(id);
    }
    /**
     * Получить страницу с отфильтрованными пользователями
     *
     * @param filter   фильтр
     * @param pageable настройки пагинации
     * @return страница с пользователями
     */
    @GetMapping
    public Page<UserByListDto> findAll(@RequestBody UserFilter filter, Pageable pageable) {
        return userService.findAll(filter, pageable);
    }

}
