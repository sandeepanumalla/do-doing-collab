package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     boolean existsByEmail(String email);
     boolean existsByUsername(String username);

     Optional<User> findUserByUsername(String username);
     Optional<User> findUserByEmail(String email);

     @Query("UPDATE User u SET u.password = ?1 WHERE u.email = ?2")
     boolean updatePasswordByEmail(String password, String email);
}
