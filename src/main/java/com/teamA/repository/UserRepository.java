package com.teamA.repository;

import com.teamA.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> getUserById(Long id);

    User getUserByEmail(String email);



}
