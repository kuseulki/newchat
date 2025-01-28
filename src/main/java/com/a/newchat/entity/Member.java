package com.a.newchat.entity;

import com.a.newchat.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id") @Id
    Long id;

    String email;
    String nickName;
    String name;
    String password;

    @Enumerated(EnumType.STRING) Gender gender;
    String phoneNumber;
    LocalDate birthDay;
    String role;
}
