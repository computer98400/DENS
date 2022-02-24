package com.ssafy.BackEnd.service;

import com.ssafy.BackEnd.dto.ChatDTO;

import java.util.List;
import java.util.Set;

public interface IChatService {

    public Set<String> getRoomList() throws Exception;

    public List<ChatDTO> insertChat(ChatDTO pDTO) throws Exception;

    public List<ChatDTO> getChat(ChatDTO pDTO) throws Exception;
}
