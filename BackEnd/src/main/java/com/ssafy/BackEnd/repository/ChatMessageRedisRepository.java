package com.ssafy.BackEnd.repository;

import com.ssafy.BackEnd.entity.ChatMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatMessageRedisRepository extends CrudRepository<ChatMessage, String> {

    List<ChatMessage> findByRoomId(String roomId);

}
