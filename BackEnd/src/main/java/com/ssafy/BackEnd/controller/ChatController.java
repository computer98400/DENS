package com.ssafy.BackEnd.controller;
import com.ssafy.BackEnd.entity.ChatMessage;

import com.ssafy.BackEnd.entity.Profile;
import com.ssafy.BackEnd.repository.ChatMessageRedisRepository;
import com.ssafy.BackEnd.repository.ProfileRepository;
import com.ssafy.BackEnd.service.jwt.JwtServiceImpl;
import com.ssafy.BackEnd.service.chat.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Controller
@Api(tags = "채팅 컨트롤러 Api")
public class ChatController {


    private final ChatMessageRedisRepository chatMessageRedisRepository;

    private final ProfileRepository profileRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final SimpMessageSendingOperations messagingTemplate;

    private final ChannelTopic channelTopic;

    private final JwtServiceImpl jwtService;

    private final RedisUtil redisUtil;



    private static final String CHAT_MESSAGES = "CHAT_MESSAGES";

    private static final Logger logger = LogManager.getLogger(ChatController.class);



    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/{roomId}")
    public void message(ChatMessage message, @Header("token") String token, @DestinationVariable("roomId") String roomId) {
        String email = jwtService.getUserEmail(token);
        System.out.println(email);
        Profile profile = profileRepository.findByEmail(email).get();
        System.out.println(profile.getName());
        String name = profile.getName();
        message.setSender(name);
        message.setSenderId(profile.getProfile_id());
        message.setTime(LocalDateTime.now());

        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setSender("[알림]");
            message.setSenderId(null);
            message.setMessage(name + "님이 입장하셨습니다.");
        }
        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
//        ChannelTopic channel = channels.get(roomId);
//        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
//        simpMessagingTemplate.convertAndSend("/topic/"+message.getRoomId(), message);
        messagingTemplate.convertAndSend("/topic/chat/room/"+message.getRoomId(), message);

        System.out.println(roomId);
        if (!message.getSender().equals("[알림]")) {
            ChatMessage save = chatMessageRedisRepository.save(message);
            logger.info("save message success");
            System.out.println(save.getMessage());
            if (save == null) {
                System.out.println("save message error");
            }
        }
    }


    @GetMapping("/chat/messages/{roomId}")
    @ResponseBody
    @ApiOperation(value="채팅방 메세지 전체 조회")
    public List<ChatMessage> messages(@PathVariable String roomId) {
        return chatMessageRedisRepository.findByRoomId(roomId);
    }
}



