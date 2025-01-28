package com.a.newchat.repository;

import com.a.newchat.entity.MemberChatroomMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberChatroomMappingRepository extends JpaRepository<MemberChatroomMapping, Long> {

    Boolean existsByMemberIdAndChatroomId(Long memberId, Long chatroomId);
    void deleteByMemberIdAndChatroomId(Long memberId, Long chatroomId);

    List<MemberChatroomMapping> findAllByMemberId(Long memberId);
}
