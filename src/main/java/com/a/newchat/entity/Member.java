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
    @Column(name = "member_id")
    @Id
    Long id;

    String email;
    String nickName;
    String name;
    String password;
    @Enumerated(EnumType.STRING)
    Gender gender;
    String phoneNumber;
    LocalDate birthDay;
    String role;

    public void updatePassword(String password, String confirmedPassword, PasswordEncoder passwordEncoder) {
        if(!password.equals(confirmedPassword)) {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }
        this.password = passwordEncoder.encode(password);
    }
}

