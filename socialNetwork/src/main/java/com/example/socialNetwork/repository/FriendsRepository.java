package com.example.socialNetwork.repository;

import com.example.socialNetwork.domain.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Компонент для работы с таблицей друзей в БД
 */
@Repository
public interface FriendsRepository extends JpaRepository<Friends, Integer> {
    List<Friends> findByFirstFriend(Integer id);
    List<Friends> findBySecondFriend(Integer id);
    Friends findByFirstFriendAndSecondFriend(Integer firstFriend, Integer secondFriend);
}
