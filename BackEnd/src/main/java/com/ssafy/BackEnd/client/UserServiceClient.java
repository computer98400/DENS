package com.ssafy.BackEnd.client;


import com.ssafy.BackEnd.dto.UserIdDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "member", url="member")
public interface UserServiceClient {
    @GetMapping(value = "/member/userId")
    UserIdDto getUserId(@RequestHeader("X-AUTH-TOKEN") String xAuthToken);
}
