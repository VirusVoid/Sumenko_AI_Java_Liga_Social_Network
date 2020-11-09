package com.example.socialNetwork.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * Данные пользователя для регистрации
 */
@Data
@NoArgsConstructor
public class UserByListDto {
    /**
     * Идентификатор пользователя
     */
    private Integer id;
    /**
     * Имя и фамилия пользователя
     */
    private String userName;
    /**
     * Интересы
     */
    private String interests;
    /**
     * Город
     */
    private String city;
}
