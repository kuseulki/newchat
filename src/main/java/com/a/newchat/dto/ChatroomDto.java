package com.a.newchat.dto;

import com.a.newchat.entity.Chatroom;
import java.time.LocalDateTime;

public record ChatroomDto(
        Long id,
        String title,
        Integer memberCount,
        LocalDateTime createdAt
) {
    public static ChatroomDto from(Chatroom chatroom) {
        return new ChatroomDto(chatroom.getId(), chatroom.getTitle(), chatroom.getMemberChatroomMappingSet().size(), chatroom.getCreatedAt());

    }
}
