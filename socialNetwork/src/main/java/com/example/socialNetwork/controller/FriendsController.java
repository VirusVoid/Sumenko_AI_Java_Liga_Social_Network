package com.example.socialNetwork.controller;

import com.example.socialNetwork.domain.Friends;
import com.example.socialNetwork.service.FriendsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("friend")
@RequiredArgsConstructor
@Slf4j
public class FriendsController {

    private final FriendsService friendsService;

    /**
     * Создать новую заявку в друзья
     * @param friends заявка в друзья
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createFriend(@RequestBody @Valid Friends friends){
      //  log.info("Регистрация нового пользователя user={}", userRegisterDto.toString());
        return friendsService.createFriendRequest(friends);
    }

    /**
     * Обновить данные существующей заявки в друзья
     * @param friends
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFriendRequest(@PathVariable Integer id, @RequestBody @Valid Friends friends){
        //  log.info("Регистрация нового пользователя user={}", userRegisterDto.toString());
        return friendsService.updateFriendRequest(id, friends);
    }
}
