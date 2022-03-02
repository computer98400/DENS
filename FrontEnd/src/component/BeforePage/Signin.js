
import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import styled from 'styled-components'
import { useDispatch, useSelector } from 'react-redux'
import { getId, getTOKEN, loginUser } from '../../redux/userreduce'
import { store } from '../..'
import { useCookies } from 'react-cookie'
import { signin, signup, test11 } from '../../api/test'
import axios from 'axios'
import { apiInstance } from '../../api'

function Login() {
  const dispatch = useDispatch()
  const [token, setToken] = useCookies(['token'])
  const [profileid, setProfileid] = useCookies(['myID'])

  const token2 = useSelector((state) => state)
  let navigate = useNavigate()

  const [input, setInput] = useState({
    email: '',
    password: '',
  })

  const changeCheck = (e) => {
    const { name, value } = e.target
    setInput({
      ...input,
      [name]: value,
    })
  }

  const LoginConsole = () => {
    console.log(input);
    signin(input, (response) => {
     console.log(response.data);
      dispatch(loginUser(response.data));
      // dispatch(getId());
      // console.log(store.getState().user.token);
      if (store.getState().user.profileid == -1) {
        return navigate(`/asdfsasfsafsfd`)
      }
      setToken('token', store.getState().user.token);
      setProfileid('myID', store.getState().user.profileid);
      navigate(`/auth`);
    }, (error) => {
      console.log(error);
    });
    // console.log(cookies);
  }
  return (
      <LoginBox>
        <H3>로그인</H3>
        <Container>
          <Name>이름</Name>
          <InputSquare  placeholder="이름을 입력해주세요." name="email" onChange={changeCheck}/>
        </Container>

        <Container>
          <Name>비밀번호</Name>
          <InputSquare type="password" placeholder="비밀번호" name="password" onChange={changeCheck}/>
        </Container>
        <Btn onClick={LoginConsole}>로그인</Btn>
        <SignUpBtn onClick={() => navigate("/signup") }>회원가입</SignUpBtn>
        <FindIdPw onClick={() => navigate("/password")}>아이디/비밀번호 찾기</FindIdPw>
      </LoginBox>
    );
}

const LoginBox = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform:translate(-50%, -50%);

  // border: 1px solid; // 위치 확인용
  display: flex;
  flex-direction: column;
  background-color: white;
  padding: 20px;
`;

const H3 = styled.h3`
  text-align: center;
  color: #F46A72; 
  font-family : 'Cafe24Ssurround';
`;

const Btn = styled.button`
  width:140px;
  position: relative;
  left: 50%;
  transform:translate(-50%, 0%);
  background-color: #F46A72; 
  color: white;
  border: none;
  border-radius: 2px;

  margin-top: 4%;
  width: 30%;
  height: 3.5vh;
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 20px;
`;

const Name = styled.span`
  font-family : 'Cafe24SsurroundAir';  
  font-style: normal;
  font-weight: 700;
  width: 150px;
  height: 18px;
  margin-left: 4px;
  `;
  
const InputSquare = styled.input`
font-family : 'Cafe24SsurroundAir'; 
  font-style: normal;
  font-weight: 700;
  margin-top: 5px;
  margin-left: 4px;
  margin-right: 4px;

  width: 374px;
  height: 36px;
  background-color: rgba(255,255,255,1);
  border-width: 1px;
  border-color: rgba(220,220,220,1);
  border-radius: 4px;
  display: flex;
  flex-direction: column;
  border-style: solid;
`

const SignUpBtn = styled.a`
  text-align: center;
  margin-top: 5%;
  color: #F46A72;
  font-family : 'Cafe24SsurroundAir'; 
  text-decoration:none;
`

const FindIdPw = styled.a`
  text-align: center;
  margin-top: 2%;
  font-family : 'Cafe24SsurroundAir'; 
  color: #F46A72;
  text-decoration:none;
`


export default Login;
