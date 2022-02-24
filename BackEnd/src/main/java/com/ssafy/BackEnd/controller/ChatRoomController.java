//
//package com.ssafy.BackEnd.controller;
//
//import com.ssafy.BackEnd.dto.ChatMessage;
//import com.ssafy.BackEnd.pubsub.RedisPublisher;
//import com.ssafy.BackEnd.repository.ChatRoomRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.stereotype.Controller;
//
//@RequiredArgsConstructor
//@Controller
//public class ChatController {
//
//    private final RedisPublisher redisPublisher;
//    private final ChatRoomRepository chatRoomRepository;
//
//    /**
//     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
//     */
//    @MessageMapping("/chat/message")
//    public void message(ChatMessage message) {
//        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
//            chatRoomRepository.enterChatRoom(message.getRoomId());
//            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
//        }
//        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
//        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
//    }
//}

//    private final ChatService chatService;
//
//    @PostMapping
//    public ChatRoom createRoom(@RequestParam String name){
//        return chatService.createRoom(name);
//    }
//
//    @GetMapping
//    public List<ChatRoom> findAllRoom() {
//        return chatService.findAllRoom();
//    }

//}

//// 은서
//
//import com.ssafy.BackEnd.client.UserServiceClient;
//import com.ssafy.BackEnd.dto.UserIdDto;
//import com.ssafy.BackEnd.entity.ChatRoom;
//import com.ssafy.BackEnd.service.ChatService;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/chat")
//class ChatRoomController {
//
//    private final ChatService chatService;
//    private final UserServiceClient userServiceClient;
//
//    @ApiOperation(value = "room 전체 조회", notes = "채팅 룸 전체를 조회한다.")
//    @GetMapping("/rooms")
//    public ResponseEntity<List<ChatRoom>> rooms() {
//        List<ChatRoom> rooms = chatService.findAllRoom();
//        return new ResponseEntity<>(rooms, HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "채팅창 개설", notes = "채팅방을 개설한다.")
//    @PostMapping("/room")
//    public ResponseEntity<ChatRoom> createRoom(@RequestHeader("X-AUTH-TOKEN") String xAuthToken, @RequestBody UserIdDto store) {
//        UserIdDto customer = userServiceClient.getUserId(xAuthToken);
//        ChatRoom chatRoom = chatService.createChatRoom(customer, store);
//        return new ResponseEntity<>(chatRoom, HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "방 정보 보기", notes = "방 정보")
//    @GetMapping("/room/{roomId}")
//    public ResponseEntity<ChatRoom> roomInfo(@PathVariable String roomId) {
//        ChatRoom room = chatService.findRoomById(roomId);
//        return new ResponseEntity<>(room, HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "customer 방 조회")
//    @GetMapping("/customer")
//    public ResponseEntity<List<ChatRoom>> getRoomsByCustomer(@RequestHeader("X-AUTH-TOKEN") String xAuthToken) {
//        UserIdDto customer = userServiceClient.getUserId(xAuthToken);
//        List<ChatRoom> customerEnterRooms = chatService.getCustomerEnterRooms(customer);
//        return new ResponseEntity<>(customerEnterRooms, HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "store 방 조회")
//    @GetMapping("/store")
//    public ResponseEntity<List<ChatRoom>> getRoomsByStore(@RequestHeader("X-AUTH-TOKEN") String xAuthToken) {
//        UserIdDto store = userServiceClient.getUserId(xAuthToken);
//        List<ChatRoom> customerEnterRooms = chatService.getCustomerEnterRooms(store);
//        return new ResponseEntity<> (customerEnterRooms, HttpStatus.OK);
//    }
//
//}

package com.ssafy.BackEnd.controller;

import com.ssafy.BackEnd.dto.ChatUserDto;
import com.ssafy.BackEnd.entity.*;
import com.ssafy.BackEnd.exception.CustomException;
import com.ssafy.BackEnd.exception.ErrorCode;
import com.ssafy.BackEnd.pubsub.RedisSubscriber;
import com.ssafy.BackEnd.repository.ChatRoomRedisRepository;
import com.ssafy.BackEnd.repository.ProfileRepository;
import com.ssafy.BackEnd.repository.UserRepository;
import com.ssafy.BackEnd.service.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/chat")
public class ChatRoomController {

    private static final Logger logger = LogManager.getLogger(ChatRoomController.class);

    private final ChatRoomRedisRepository chatRoomRedisRepository;

    private final ProfileRepository profileRepository;

    private final JwtServiceImpl jwtService;

    private Map<String, ChannelTopic> channels;

    private final RedisSubscriber redisSubscriber;

    private final RedisMessageListenerContainer redisMessageListenerContainer;


    @GetMapping("/rooms")
    public ResponseEntity<Iterable<ChatRoom>> rooms() {
        Iterable<ChatRoom> all = chatRoomRedisRepository.findAll();
        return new ResponseEntity<Iterable<ChatRoom>>(all, HttpStatus.OK);
    }

    @GetMapping("/rooms/{profileId}")
    public ResponseEntity<Iterable<ChatRoom>> room(@PathVariable Long profileId) {
        Iterable<ChatRoom> chatRooms = chatRoomRedisRepository.findAll();
        Profile profile = profileRepository.findById(profileId).get();
        List<ChatRoom> result = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms) {
            if (chatRoom.getName().contains(profile.getName()+profileId)) {
                result.add(chatRoom);
                logger.info(chatRoom.getName());
            }
        }
        logger.info("get chatrooms success");
        return new ResponseEntity<Iterable<ChatRoom>>(result, HttpStatus.OK);
    }

    @PostMapping("/room/{profileId1}/{profileId2}")
    public ResponseEntity<ChatRoom> createRoom(@PathVariable Long profileId1, @PathVariable Long profileId2) {
        Profile profile1 = profileRepository.findById(profileId1).get();
        ChatUserDto user1 = new ChatUserDto();
        ChatUserDto chatUser1 = user1.builder().profileId(profileId1).name(profile1.getName()).build();
        Profile profile2 = profileRepository.findById(profileId2).get();
        ChatUserDto user2 = new ChatUserDto();
        ChatUserDto chatUser2 = user2.builder().profileId(profileId2).name(profile2.getName()).build();

        ChatRoom findRoom1 = chatRoomRedisRepository.findByName(chatUser1.getName()+chatUser1.getProfileId() +"_"+ chatUser2.getName()+chatUser2.getProfileId());
        ChatRoom findRoom2 = chatRoomRedisRepository.findByName(chatUser2.getName()+chatUser2.getProfileId() +"_"+ chatUser1.getName()+chatUser1.getProfileId());
        if (findRoom1 == null && findRoom2 == null){
            ChatRoom chatRoom = ChatRoom.create(chatUser1, chatUser2);
            ChatRoom save = chatRoomRedisRepository.save(chatRoom);
            logger.info("CreateRoom success");
            return new ResponseEntity<ChatRoom>(save, HttpStatus.CREATED);
        } else if (findRoom1 == null && findRoom2 != null) {
            logger.error("chatroom is already exists");
//            throw new CustomException(ErrorCode.ALREADY_EXISTS_CHATROOM);
            return new ResponseEntity<ChatRoom>(findRoom2, HttpStatus.FOUND);
        } else if (findRoom1 != null && findRoom2 == null) {
            logger.error("Chatroom is already exists");
//            throw new CustomException(ErrorCode.ALREADY_EXISTS_CHATROOM);
            return new ResponseEntity<ChatRoom>(findRoom1, HttpStatus.FOUND);
        } else {
            logger.error("cannot create room");
            throw new CustomException(ErrorCode.CANNOT_CREATE_CHATROOM);
        }
    }

    @GetMapping("/room/enter/{roomId}/{profileId}")
    public ResponseEntity<ChatRoom> enterRoom(Model model, @PathVariable String roomId, @PathVariable Long profileId) {
        Profile profile = profileRepository.findById(profileId).get();
        ChatRoom findRoom = chatRoomRedisRepository.findByRoomId(roomId);
        if (findRoom.getUser1().getProfileId().equals(profileId) || findRoom.getUser2().getProfileId().equals(profileId)) {
            return new ResponseEntity<ChatRoom>(findRoom, HttpStatus.OK);
        }
        throw new CustomException(ErrorCode.NO_CHAT_ROOM);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<ChatRoom> roomInfo(@PathVariable String roomId) {
        ChatRoom chatRoom = chatRoomRedisRepository.findByRoomId(roomId);
        if (chatRoom == null) {
            logger.info("get room info success");
            throw new CustomException(ErrorCode.NO_CHAT_ROOM);
        }
        return new ResponseEntity<ChatRoom>(chatRoom, HttpStatus.OK);
    }

    @GetMapping("/user")
    public LoginInfo getUserInfo(HttpServletRequest request) {
        String authorization = jwtService.resolveToken(request);
        String email = jwtService.getUserEmail(authorization);
        Profile profile = profileRepository.findByEmail(email).get();
        String name = profile.getName();
        return LoginInfo.builder().name(name).token(authorization).build();
    }
//
//    @GetMapping("/user")
//    public void getUserInfo(HttpServletRequest request) {
//        String authorization = request.getHeader("Authorization");
//        System.out.println(authorization);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        auth.getDetails()
//        return LoginInfo.builder().name(name).token(jwtService.createToken(name)).build();

//    @GetMapping("/user")
//    @ResponseBody
//    public LoginInfo getUserInfo() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        return LoginInfo.builder().name(name).token(jwtService.createToken(name)).build();
//    }
}
