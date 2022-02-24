package com.ssafy.BackEnd.persistence.redis;

import com.ssafy.BackEnd.dto.ChatDTO;
import com.ssafy.BackEnd.util.CmmUtil;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Component("ChatMapper")
public class ChatMapper implements IChatMapper, IContRedis{

    @Autowired
    public RedisTemplate<String, Object> redisDB;

    private Logger log = LogManager.getLogger(this.getClass());

    @Override
    public Set<String> getRoomList() throws Exception {
        log.info(this.getClass().getName() + ".getRoomList Start!");

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new StringRedisSerializer());

        Set<String> rSet = (Set) redisDB.keys("*");

        log.info(this.getClass().getName() + ".getRoomList End!");

        return rSet;
    }

    @Override
    public int insertChat(ChatDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".insertChat Start!");

        int res = 0;

        String roomKey = CmmUtil.nvl(pDTO.getRoomId());

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatDTO.class));

        redisDB.opsForList().rightPush(roomKey, pDTO);

        res = 1;

        log.info(this.getClass().getName() + ".insertChat End!");

        return res;
    }

    @Override
    public List<ChatDTO> getChat(ChatDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getChat Start!");

        List<ChatDTO> rList = null;

        String roomKey = CmmUtil.nvl(pDTO.getRoomId());

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatDTO.class));

        if(redisDB.hasKey(roomKey)) {

            rList = (List) redisDB.opsForList().range(roomKey, 0, -1);

            if(rList == null) {
                rList = new LinkedList<ChatDTO>();
            }
        }

        log.info(this.getClass().getName() + ".getChat Ebd!");

        return rList;
    }

    @Override
    public boolean setTimeOutHour(String roomId, int hours) throws Exception {
        log.info(this.getClass().getName() + ".setTimeOutHour Start!");
        return redisDB.expire(roomId, hours, TimeUnit.HOURS);
    }

    @Override
    public boolean setTimeOutMinute(String roomId, int minutes) throws Exception {
        log.info(this.getClass().getName() + ".setTimeOutMinute Start!");
        return redisDB.expire(roomId, minutes, TimeUnit.MINUTES);
    }
}
