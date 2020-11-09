package com.example.socialNetwork.service;

import com.example.socialNetwork.domain.PersonalPages;
import com.example.socialNetwork.domain.Users;
import com.example.socialNetwork.dto.PersonalPageEditDto;
import com.example.socialNetwork.dto.UserRegistryDto;
import com.example.socialNetwork.repository.PersonalPageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PersonalPageService {
    private final PersonalPageRepository pageRepository;

    private final UserService userService;

    /**
     * Проверка данных пользователя и внесение их в БД
     *
     * @param personalPage регистрационные данные пользователя
     * @return
     */
    @Transactional
    public ResponseEntity createPersonalPage(PersonalPages personalPage) {
        // Users users1 = convertUserPersPageDtoToUser(personalPageDto, new Users());

        UserRegistryDto user = userService.findUserById(personalPage.getUser_id());
        if (user != null) {
            PersonalPages page = pageRepository.save(personalPage);
            return new ResponseEntity<>(page.getId(), HttpStatus.OK);
        } else return new ResponseEntity<>("Пользователь с user_id=" +
                personalPage.getUser_id() + " не найден",
                HttpStatus.NOT_FOUND);
    }

    public PersonalPages getPersonalPage(Integer id) {
        return pageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    /**
     * Обновление данных пользователя и внесение их в БД
     *
     * @param updatedPage данные пользователя
     */
    @Transactional
    public Integer updatePersonalPage(Integer id, PersonalPages updatedPage) {
        PersonalPages foundPage = Optional.ofNullable(id)
                .flatMap(pageRepository::findById)
                .orElseThrow(() -> new RuntimeException("Персональная страница пользователя не найдена"));
        PersonalPages page = updatePersonalPageInformation(updatedPage, foundPage);
        page = pageRepository.save(page);
        return page.getId();
    }

    /**
     * Удаление персональной страницы пользователя
     *
     * @param id идентификатор персональной страницы
     * @return
     */
    @Transactional
    public Integer deletePersonalPage(Integer id) {
        PersonalPages personalPage = Optional.ofNullable(id)
                .flatMap(pageRepository::findById)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        if (personalPage != null)
            pageRepository.delete(personalPage);
        return id;
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

    private Users convertUserPersPageDtoToUser(PersonalPageEditDto dto, Users entity) {
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setAge(dto.getAge());
        entity.setGender(dto.getGender());
        entity.setInterests(dto.getInterests());
        entity.setCity(dto.getCity());
        // entity.setEducation(dto.getEducation());

        return entity;
    }

    /**
     * Преобразование DTO-сущности в {@link Users}
     *
     * @param received данные о пользователе
     * @return пользователь
     */
    private PersonalPages updatePersonalPageInformation(PersonalPages received, PersonalPages found) {
        found.setUser_id(received.getUser_id());
        found.setName(received.getName());
        found.setSurname(received.getSurname());
        found.setAge(received.getAge());
        found.setGender(received.getGender());
        found.setInterests(received.getInterests());
        found.setCity(received.getCity());
        found.setLanguages(received.getLanguages());
        found.setEducation(received.getEducation());
        found.setJob(received.getJob());
        found.setLife_phylosophy(received.getLife_phylosophy());

        return found;
    }


}
