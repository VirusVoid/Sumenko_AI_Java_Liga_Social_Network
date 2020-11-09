package com.example.socialNetwork.service.filters;

import com.example.socialNetwork.domain.Users;
import com.example.socialNetwork.service.specification.BaseSpecification;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import static org.springframework.data.jpa.domain.Specification.where;

@Getter
@Setter
public class UserFilter implements Filter<Users> {
    /**
     * Имя
     */
    private String nameLike;
    /**
     * Фамилия
     */
    private String surnameLike;
    /**
     * Возраст
     */
    private Integer ageLike;
    /**
     * Пол
     */
    private String genderLike;
    /**
     * Интересы
     */
    private String interestsLike;
    /**
     * Город
     */
    private String cityLike;


    @Override
    public Specification<Users> toSpecification() {
        return where(BaseSpecification.<Users>like("name", nameLike))
                .and(BaseSpecification.like("surname", surnameLike))
                .and(BaseSpecification.equal("age", ageLike))
                .and(BaseSpecification.equal("gender", genderLike))
                .and(BaseSpecification.like("interests", interestsLike))
                .and(BaseSpecification.like("city", cityLike));
    }
}
