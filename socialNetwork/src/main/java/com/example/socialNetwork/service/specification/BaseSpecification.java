package com.example.socialNetwork.service.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class BaseSpecification {

    /**
     * Поиск по вхождению
     *
     * @param column колонка таблицы сущности T
     * @param value значения для поиска
     * @return спецификация
     */
    public static <T> Specification<T> like(final String column, final String value) {
        return StringUtils.isEmpty(column) || StringUtils.isEmpty(value)
                ? null
                : (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(column)),
                        "%" + value.toLowerCase() + "%");
    }

    /**
     * Поиск по эквивалентности полю
     *
     * @param column колонка таблицы сущности T
     * @param flag   значение поля
     * @return спецификация
     */
    public static <T> Specification<T> equal(final String column, final Object flag) {
        return StringUtils.isEmpty(column) || ObjectUtils.isEmpty(flag)
                ? null
                : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(column), flag);
    }

}
