package com.ssafy.BackEnd.repository;

import com.ssafy.BackEnd.entity.ChatMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface ChatMessageRedisRepository extends CrudRepository<ChatMessage, String> {

    List<ChatMessage> findByRoomId(String roomId);

}
