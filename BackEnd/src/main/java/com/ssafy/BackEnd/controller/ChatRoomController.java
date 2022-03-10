package com.ssafy.BackEnd.controller;

import com.ssafy.BackEnd.dto.ChatUserDto;
import com.ssafy.BackEnd.entity.*;
import com.ssafy.BackEnd.exception.CustomException;
import com.ssafy.BackEnd.exception.ErrorCode;
import com.ssafy.BackEnd.pubsub.RedisSubscriber;
import com.ssafy.BackEnd.repository.ChatRoomRedisRepository;
import com.ssafy.BackEnd.repository.ProfileRepository;
import com.ssafy.BackEnd.service.jwt.JwtServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/chat")
@Api(tags = "채팅방 컨트롤러 Api")
public class ChatRoomController {

    private static final Logger logger = LogManager.getLogger(ChatRoomController.class);

    private final ChatRoomRedisRepository chatRoomRedisRepository;

    private final ProfileRepository profileRepository;

    private final JwtServiceImpl jwtService;

    private Map<String, ChannelTopic> channels;

    private final RedisSubscriber redisSubscriber;

    private final RedisMessageListenerContainer redisMessageListenerContainer;


    @GetMapping("/rooms")
    @ApiOperation(value = "채팅방 전체 조회")
    public ResponseEntity<Iterable<ChatRoom>> rooms() {
        Iterable<ChatRoom> all = chatRoomRedisRepository.findAll();
        return new ResponseEntity<Iterable<ChatRoom>>(all, HttpStatus.OK);
    }

    @GetMapping("/rooms/{profileId}")
    @ApiOperation(value = "유저가 속한 채팅방 조회")
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
    @ApiOperation(value="채팅방 생성")
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
            return new ResponseEntity<ChatRoom>(findRoom2, HttpStatus.FOUND);
        } else if (findRoom1 != null && findRoom2 == null) {
            logger.error("Chatroom is already exists");
            return new ResponseEntity<ChatRoom>(findRoom1, HttpStatus.FOUND);
        } else {
            logger.error("cannot create room");
            throw new CustomException(ErrorCode.CANNOT_CREATE_CHATROOM);
        }
    }

    @GetMapping("/room/enter/{roomId}/{profileId}")
    @ApiOperation(value="채팅방 입장")
    public ResponseEntity<ChatRoom> enterRoom(Model model, @PathVariable String roomId, @PathVariable Long profileId) {
        Profile profile = profileRepository.findById(profileId).get();
        ChatRoom findRoom = chatRoomRedisRepository.findByRoomId(roomId);
        if (findRoom.getUser1().getProfileId().equals(profileId) || findRoom.getUser2().getProfileId().equals(profileId)) {
            return new ResponseEntity<ChatRoom>(findRoom, HttpStatus.OK);
        }
        throw new CustomException(ErrorCode.NO_CHAT_ROOM);
    }

    @GetMapping("/room/{roomId}")
    @ApiOperation(value = "채팅방 정보 조회")
    public ResponseEntity<ChatRoom> roomInfo(@PathVariable String roomId) {
        ChatRoom chatRoom = chatRoomRedisRepository.findByRoomId(roomId);
        if (chatRoom == null) {
            logger.info("get room info success");
            throw new CustomException(ErrorCode.NO_CHAT_ROOM);
        }
        return new ResponseEntity<ChatRoom>(chatRoom, HttpStatus.OK);
    }

    @GetMapping("/user")
    @ApiOperation(value = "유저 정보 가져오기")
    public LoginInfo getUserInfo(HttpServletRequest request) {
        String authorization = jwtService.resolveToken(request);
        String email = jwtService.getUserEmail(authorization);
        Profile profile = profileRepository.findByEmail(email).get();
        String name = profile.getName();
        return LoginInfo.builder().name(name).token(authorization).build();
    }
}
