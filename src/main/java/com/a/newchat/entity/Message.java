package com.a.newchat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    Long id;

    String text;

    @JoinColumn(name = "member_id") @ManyToOne
    Member member;

    @JoinColumn(name = "chatroom_id") @ManyToOne
    Chatroom chatroom;

    LocalDateTime createdAt;
}
