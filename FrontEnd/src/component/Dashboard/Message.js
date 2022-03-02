
import axios from 'axios';
import React, { Component, useEffect, useState } from "react";
import styled, { css } from "styled-components";
import { store } from '../..';
import { API_BASE_URL } from '../../config';
import { Link} from 'react-router-dom'

function Message(props) {
  const profileId = store.getState().user.profileid;
  const token = store.getState().user.token;
  const [data, setData] = useState(null);
  const authAxios = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        Authorization: `Bearer "${token}"`,
        withCredentials : true,
    }
  })
  const [roomid, setRoomid] = useState('');
  useEffect(() => {
   getData();
    // recentmessage();
  },[])
  //1. CHAT/ROOMS/프로필아이디 -> 
  
  //2. 룸아이디 가져오기
  const getData = () => {
    authAxios.get(`/chat/rooms/${profileId}`)
    .then((res) => {
      setData(res.data);
      console.log(res);
      // setRoomid(res.data[0].roomid);
    })
      .catch((error) => { console.log(error) });
  };

  const recentmessage = () => {
    authAxios.get(`/chat/messages/${data.roomid}`).then((res) => {
      console.log(res);
    }).catch((err)=> { console.log(err)});
  }

  return (
    <Container {...props}>
      <Title>메시지</Title>
      <Rect>
        <MessageTitle>최근 메세지를 보낸 사람</MessageTitle>
        <hr ></hr>
          {data ? data.map((val, key) => {
            return (
    
              <div key={key} align='center'>                  
                  {
                    profileId === val.user1.profileId
                    
                    ? <Link
                        to={{
                          pathname:`/auth/messenger/${val.roomId}`,
                        }}
                      >
                        {val.user2.name}                      
                      </Link>
                    : <Name align='center'>{val.user2.name}</Name>
                  }
              </div>
            )
          }): ``}
      </Rect>
    </Container>
  );
}

const Container = styled.div`
  // display: flex;
  // justify-content: column;
  margin-top: 2vh;
`;

const Title = styled.h3`
  font-family : 'Cafe24Ssurround';
  font-style: normal;
  font-weight: 700;
  color: rgba(244,106,114,1);
  height: 22px;
  width: 90px;
  // margin-left: 3px;
`;

const Rect = styled.div`
  width: 100%;
  height: 20vh;
  margin-top: 40px;
  margin-bottom: 30px;

  box-shadow: 3px 3px 13px rgba(244,106,114,1);
  background-color: white;
`;


const MessageTitle = styled.p`
  font-family : 'Cafe24SsurroundAir';
  color: Black;
  text-align: center;
  vertical-align: middle;
  margin-top: 20px;
  padding-top: 20px;
`

const Name = styled.p`
  font-family : 'Cafe24SsurroundAir';
  color: Black;
  align: 'center';

  `

const NameDiv = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
`

export default Message;