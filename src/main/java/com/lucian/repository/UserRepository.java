package com.lucian.repository;


import com.lucian.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);

//    @Query(value = "select u from users u where lower(u.email) = lower(:email) and u.isActive = true")
//    Optional<UserEntity> findByEmailIgnoreCaseAndIsActive(String email);
//
//    @Query(value = "select u from users u where lower(u.email) = lower(:email)")
//    Optional<UserEntity> findByEmailIgnoreCase(String email);


}



