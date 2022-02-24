package com.ssafy.BackEnd.entity;

import com.ssafy.BackEnd.dto.ChatUserDto;
import com.ssafy.BackEnd.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@RedisHash("chatrooms")
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;
    @Id
    private String id;

    @Indexed
    private String roomId;
    @Indexed
    private String name;
    @Indexed
    private ChatUserDto user1;
    @Indexed
    private ChatUserDto user2;

    public static ChatRoom create(ChatUserDto user1, ChatUserDto user2) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = user1.getName()+user1.getProfileId()+ "_" + user2.getName() + user2.getProfileId();
        chatRoom.user1 = user1;
        chatRoom.user2 = user2;
        return chatRoom;
    }
}

//    public void addChatMessages(ChatMessage chatMessage) {
//        this.chatMessages.add(chatMessage);
//    }


