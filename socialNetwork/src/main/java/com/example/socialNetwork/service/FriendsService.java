package com.example.socialNetwork.service;

import com.example.socialNetwork.domain.Friends;
import com.example.socialNetwork.domain.Users;
import com.example.socialNetwork.repository.FriendsRepository;
import com.example.socialNetwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FriendsService {
    private final FriendsRepository friendsRepository;

    private final UserRepository userRepository;

    /**
     * Проверка данных заявки и добавление в базу данных
     *
     * @param friends регистрационные данные пользователя
     */
    @Transactional
    public ResponseEntity createFriendRequest(Friends friends) {
        System.out.println(friends.getId());
        Users user1 = userRepository.findById(friends.getFirstFriend())
                .orElse(null);
        Users user2 = userRepository.findById(friends.getSecondFriend())
                .orElse(null);
        if (user1 != null && user2 != null) {
            Friends result = friendsRepository.findByFirstFriendAndSecondFriend(friends.getFirstFriend(),
                    friends.getSecondFriend());
            Friends reverseResult = friendsRepository.findByFirstFriendAndSecondFriend(friends.getSecondFriend(),
                    friends.getFirstFriend());
            if (result == null && reverseResult == null) {
                Friends friendship = friendsRepository.save(friends);
                return new ResponseEntity<>(friendship.getId(), HttpStatus.OK);
            } else return new ResponseEntity<>("Заявка на добавление в друзья уже существует", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Проверьте входные значения", HttpStatus.BAD_REQUEST);
    }

    /**
     * Проверка данных заявки и обновление информации в базе данных
     *
     * @param friends идентификатор отправителя, получателя заявки и статус
     */
    @Transactional
    public ResponseEntity updateFriendRequest(Integer id, Friends friends) {
        Friends foundRequest = Optional.ofNullable(id)
                .flatMap(friendsRepository::findById)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена"));
        if (!friends.getFirstFriend().equals(foundRequest.getFirstFriend()) ||
                !friends.getSecondFriend().equals(foundRequest.getSecondFriend())) {
            return new ResponseEntity<>("Проверьте идентификаторы пользователей", HttpStatus.BAD_REQUEST);
        }
        Friends friend = updateFriendsInformation(friends, foundRequest);
        friend = friendsRepository.save(friend);
        return new ResponseEntity<>(friend.getId(), HttpStatus.OK);
    }

    /**
     *
     * @param received
     * @param found
     * @return
     */
    private Friends updateFriendsInformation(Friends received, Friends found) {
        found.setStatus(received.getStatus());
        System.out.println(received.getStatus());
        return found;
    }
}