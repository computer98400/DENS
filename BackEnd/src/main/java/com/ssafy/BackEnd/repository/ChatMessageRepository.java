package com.ssafy.BackEnd.repository;

import com.ssafy.BackEnd.entity.ChatMessage;
import com.ssafy.BackEnd.entity.ChatRoom;
import com.ssafy.BackEnd.pubsub.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class ChatMessageRepository {

    //    // 채팅방(topic)에 발행되는 메시지를 처리할 Listener
//    private final RedisMessageListenerContainer redisMessageListener;
//    // 구독 처리 서비스
//    private final RedisSubscriber redisSubscriber;
    // Redis
    private static final String CHAT_MESSAGES = "CHAT_MESSAGE";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatMessage> opsHashChatMessage;
//    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보, 서버별로 채팅방에 매치되는 topic 정보를 Map에 넣어 roomId로 찾을 수 있도록 한다.
//    private Map<String, ChannelTopic> topics;

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
