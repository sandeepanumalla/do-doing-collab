package com.example.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class PasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String token;

    @OneToOne( fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @ToStringExclude
    private User user;

    private Date expiryDate;
}
