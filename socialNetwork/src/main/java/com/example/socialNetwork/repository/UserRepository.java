package com.example.socialNetwork.repository;

import com.example.socialNetwork.domain.PersonalPages;
import com.example.socialNetwork.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>, JpaSpecificationExecutor<Users> {
}
