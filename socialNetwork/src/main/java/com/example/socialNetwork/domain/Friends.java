package com.example.socialNetwork.domain;

import com.example.socialNetwork.domain.enums.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotEmpty;

/**
 * Описание отношения дружбы между пользователями
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "friends")
public class Friends {
    /**
     * Идентификатор запроса дружбы
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Идентификатор пользователя, добавляющего в друзья
     */
    @Column(name = "first_friend", nullable = false)
    private Integer firstFriend;
    /**
     * Идентификатор пользователя, добавляемого в друзья
     */
    @Column(name = "second_friend", nullable = false)
    private Integer secondFriend;
    /**
     * Статус отношений 2 пользователей:
     * APPLIED : отправлен запрос на добавление в друзья;
     * ACCEPTED : запрос дружбы принят;
     * REJECTED : запрос дружбы отклонен
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
}
