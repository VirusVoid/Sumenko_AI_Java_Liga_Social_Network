package com.example.socialNetwork.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * Данные пользователя для вывода в виде списка
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
}
