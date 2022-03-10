package com.ssafy.BackEnd.repository;

import com.ssafy.BackEnd.entity.ChatRoom;
import org.springframework.data.repository.CrudRepository;

public interface ChatRoomRedisRepository extends CrudRepository<ChatRoom, String> {

    ChatRoom findByRoomId(String roomId);

    ChatRoom findByName(String name);
}
