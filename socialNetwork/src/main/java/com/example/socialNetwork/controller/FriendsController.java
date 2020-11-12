package com.example.socialNetwork.controller;

import com.example.socialNetwork.domain.Friends;
import com.example.socialNetwork.service.FriendsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Обработка HTTP-запросов для работы с таблицей друзей
 */
@Component
@RequestMapping("friend")
@RequiredArgsConstructor
public class FriendsController {

    @Autowired
    private FriendsService friendsService;

    /**
     * Создать новую заявку в друзья
     * @param friends заявка в друзья
     */
    @PostMapping
    public ResponseEntity<?> createFriend(@RequestBody @Valid Friends friends){
        return friendsService.createFriendRequest(friends);
    }

    /**
     * Обновить данные существующей заявки в друзья
     * @param friends заявка в друзья
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFriendRequest(@PathVariable Integer id, @RequestBody @Valid Friends friends){
        return friendsService.updateFriendRequest(id, friends);
    }
}