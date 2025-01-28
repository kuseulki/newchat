package com.a.newchat.service;

import com.a.newchat.entity.Chatroom;
import com.a.newchat.entity.Member;
import com.a.newchat.entity.MemberChatroomMapping;
import com.a.newchat.entity.Message;
import com.a.newchat.repository.ChatroomRepository;
import com.a.newchat.repository.MemberChatroomMappingRepository;
import com.a.newchat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatroomRepository chatroomRepository;
    private final MemberChatroomMappingRepository memberChatroomMappingRepository;
    private final MessageRepository messageRepository;

    // 채팅방 생성 (만든사람, 채팅방이름) -- 채팅방 생성 후 채팅방 만든 사용자가 채팅방에 참여
    public Chatroom createChatroom(Member member, String title) {
        Chatroom chatroom = Chatroom.builder()
                .title(title)
                .createdAt(LocalDateTime.now())
                .build();

        chatroom = chatroomRepository.save(chatroom);
        MemberChatroomMapping memberChatroomMapping = chatroom.addMember(member);
        memberChatroomMapping = memberChatroomMappingRepository.save(memberChatroomMapping);
        return chatroom;
    }

    // 다른 사람이 만든 채팅방에 참여 (참여했는지, 이미 참여한 방이기 때문에 참여x) (사용자 정보, 참여하고자 하는 채팅방 id)
    public Boolean joinChatroom(Member member, Long chatroomId) {
        if (memberChatroomMappingRepository.existsByMemberIdAndChatroomId(member.getId(), chatroomId)) {
            log.info("이미 참여한 채팅방입니다.");
            return false;
        }

        Chatroom chatroom = chatroomRepository.findById(chatroomId).get();

        MemberChatroomMapping memberChatroomMapping = MemberChatroomMapping.builder()
                .member(member)
                .chatroom(chatroom)
                .build();
        memberChatroomMapping = memberChatroomMappingRepository.save(memberChatroomMapping);
        return true;
    }

    // 이미 참여한 채팅방에서 나오기 (사용자 정보, 채팅방 id)
    @Transactional
    public Boolean leaveChatroom(Member member, Long chatroomId){
        if (!memberChatroomMappingRepository.existsByMemberIdAndChatroomId(member.getId(), chatroomId)) {
            log.info("참여하지 않은 채팅방입니다.");
            return false;
        }
        memberChatroomMappingRepository.deleteByMemberIdAndChatroomId(member.getId(), chatroomId);
        return true;
    }

    // 사용자가 참여한 모든 채팅방 목록
    public List<Chatroom> getChatroomList(Member member){
        List<MemberChatroomMapping> memberChatroomMappingList = memberChatroomMappingRepository.findAllByMemberId(member.getId());
        return memberChatroomMappingList.stream()
                .map(MemberChatroomMapping::getChatroom)
                .toList();
    }

    // 메시지 저장 (누가, 어떤내용, 어떤방에서)
    public Message saveMassage(Member member, Long chatroomId, String text){
        Chatroom chatroom = chatroomRepository.findById(chatroomId).get();

        Message message = Message.builder()
                .text(text)
                .member(member)
                .chatroom(chatroom)
                .build();
        return messageRepository.save(message);
    }

    // 특정 채팅방에서 작성된 모든 메시지 가져옴
    public List<Message> getMessageList(Long chatroomId){
        return messageRepository.findAllByChatroomId(chatroomId);
    }
}
