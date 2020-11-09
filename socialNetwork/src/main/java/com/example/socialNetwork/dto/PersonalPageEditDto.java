package com.example.socialNetwork.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PersonalPageEditDto {
    /**
     * Идентификатор персональной страницы
     */
    private Integer id;
    /**
     * Идентификатор пользователя
     */
    @NotNull
    private Integer user_id;
    /**
     * Имя
     */
    @NotEmpty
    private String name;
    /**
     * Фамилия
     */
    @NotEmpty
    private String surname;
    /**
     * Возраст
     */
    private Integer age;
    /**
     * Пол
     */
    private String gender;
    /**
     * Интересы
     */
    private String interests;
    /**
     * Город
     */
    private String city;
    /**
     * Языки
     */
    private String languages;
    /**
     * Образование
     */
    private String education;
    /**
     * Место работы
     */
    private String job;
    /**
     * Жизненная позиция
     */
    private String life_phylosophy;
}
