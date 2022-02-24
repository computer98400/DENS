package com.ssafy.BackEnd.repository;

import com.ssafy.BackEnd.entity.ChatRoom;
import com.ssafy.BackEnd.entity.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRoomRedisRepository extends CrudRepository<ChatRoom, String> {

    ChatRoom findByRoomId(String roomId);

    ChatRoom findByName(String name);
}
