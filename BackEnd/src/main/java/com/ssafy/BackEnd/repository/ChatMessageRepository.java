package com.ssafy.BackEnd.repository;

import com.ssafy.BackEnd.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class ChatMessageRepository {

    private static final String CHAT_MESSAGES = "CHAT_MESSAGE";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatMessage> opsHashChatMessage;

    @PostConstruct
    private void init() {
        opsHashChatMessage = redisTemplate.opsForHash();
    }

    public List<ChatMessage> findAllMessages(String id) {
        return opsHashChatMessage.values(id);
    }

    public ChatMessage findMessageById(String id) {
        return opsHashChatMessage.get(CHAT_MESSAGES, id);
    }

    // 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
    public ChatMessage saveMessage(ChatMessage message) {
        String roomId = message.getRoomId();
        opsHashChatMessage.put(CHAT_MESSAGES, message.getRoomId(), message);
        return message;
    }
}
