package com.example.socialNetwork.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PersonalPageByListDto {
    /**
     * Идентификатор пользователя
     */
    private Integer id;
    /**
     * Имя и фамилия пользователя
     */
    private String userName;
    /**
     * Возраст
     */
    private Integer age;
    /**
     * Город
     */
    private String city;
}
