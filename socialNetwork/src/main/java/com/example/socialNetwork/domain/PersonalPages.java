package com.example.socialNetwork.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Персональные страницы пользователя
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "personal_pages")
public class PersonalPages {
    /**
     * Идентификатор персональной страницы
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Идентификатор владельца персональной страницы
     */
    @Column(name = "user_id", nullable = false)
    private Integer user_id;
    /**
     * Имя
     */
    @Column(name = "name", length = 100, nullable = false)
    @NotEmpty
    private String name;
    /**
     * Фамилия
     */
    @Column(name = "surname", length = 100, nullable = false)
    @NotEmpty
    private String surname;
    /**
     * Возраст
     */
    @Column(name = "age")
    private Integer age;
    /**
     * Пол
     */
    @Column(name = "gender")
    private String gender;
    /**
     * Интересы
     */
    @Column(name = "interests")
    private String interests;
    /**
     * Город
     */
    @Column(name = "city")
    private String city;
    /**
     * Языки
     */
    @Column(name = "languages")
    private String languages;
    /**
     * Образование
     */
    @Column(name = "education")
    private String education;
    /**
     * Место работы
     */
    @Column(name = "job")
    private String job;
    /**
     * Жизненная позиция
     */
    @Column(name = "life_phylosophy")
    private String life_phylosophy;
}
