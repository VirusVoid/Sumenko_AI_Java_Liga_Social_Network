package com.example.socialNetwork.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * Данные пользователя для регистрации
 */
@Data
@NoArgsConstructor
public class UserRegistryDto {
    /**
     * Идентификатор пользователя
     */
    private Integer id;
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
}
