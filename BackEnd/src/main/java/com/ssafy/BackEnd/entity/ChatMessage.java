package com.ssafy.BackEnd.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter @Setter
@RedisHash("chatmessages")
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 6494678977089006638L;

    public enum MessageType {
        ENTER, TALK, JOIN
    }

    @Id
    private String id;
    private MessageType type;
    @Indexed
    private String roomId;
    private String sender;
    @Indexed
    private Long senderId;
    private String message;
    private LocalDateTime time;

}
