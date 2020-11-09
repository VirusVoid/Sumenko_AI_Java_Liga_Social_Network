package com.example.socialNetwork.controller;

import com.example.socialNetwork.domain.PersonalPages;
import com.example.socialNetwork.service.PersonalPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("page")
@RequiredArgsConstructor
@Slf4j
public class PersonalPageController {

    private final PersonalPageService pageService;

    @GetMapping("/{id}")
    public PersonalPages getPersonalPage(@PathVariable Integer id){
        //  log.info("Регистрация нового пользователя user={}", userRegisterDto.toString());
        return pageService.getPersonalPage(id);
    }
    @PostMapping
    public ResponseEntity<?> createPersonalPage(@RequestBody @Valid PersonalPages personalPage){
        //  log.info("Регистрация нового пользователя user={}", userRegisterDto.toString());
        return pageService.createPersonalPage(personalPage);
    }
    @PutMapping("/{id}")
    public Integer updatePersonalPage(@PathVariable Integer id, @RequestBody @Valid PersonalPages personalPage){
        //  log.info("Регистрация нового пользователя user={}", userRegisterDto.toString());
        return pageService.updatePersonalPage(id, personalPage);
    }
    @DeleteMapping("/{id}")
    public Integer deletePersonalPage(@PathVariable Integer id){
        //  log.info("Регистрация нового пользователя user={}", userRegisterDto.toString());
        return pageService.deletePersonalPage(id);
    }

/*
    @GetMapping("/friends")
    public String viewFriends(Users users) {
        //  List<User> friendList =
        return "friendList";
    }
    }*/
}
