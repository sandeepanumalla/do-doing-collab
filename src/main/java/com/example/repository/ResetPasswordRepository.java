package com.example.repository;

import com.example.model.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ResetPasswordRepository extends JpaRepository<PasswordToken, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM PasswordToken pt WHERE pt.user.userId = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    @Query("SELECT pt.user.userId FROM PasswordToken pt WHERE pt.token = :token")
    Long findUserIdByToken(@Param("token") String token);

//    @Query("SELECT pt FROM PasswordToken pt JOIN FETCH pt.user WHERE pt.token = :token")
    Optional<PasswordToken> findByToken( String token);
}
