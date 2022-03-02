import axios from 'axios'
import React, { useState } from 'react'
import { Cookies, useCookies } from 'react-cookie'
import { useNavigate, Link } from 'react-router-dom'
import { API_BASE_URL } from '../../config'
import { store } from '../..'
import { useEffect } from 'react'
import styled from 'styled-components'
export default function MessangerCard({ data }) {
  const singleData = data
  const cookie = useCookies(['token'])
  const [talkingLog, setTalkingLog] = useState([])
  const navigate = useNavigate()
  const [yourId, setYourId] = useState('')
  useEffect(() => {
    setYourId(data.user2.profileId)
  }, [])

  const token = store.getState().user.token
  const profileId = store.getState().user.profileid
  const handleClick = () => {
    console.log('clickCheck', data)
    const authAxios = axios.create({
      baseURL: API_BASE_URL,
      headers: {
        Authorization: `Bearer "${token}"`,
        withCredentials: true,
      },
    })
    // authAxios
    //   .get(`/chat/room/enter/${singleData.roomId}/${profileId}`)
    //   .then(navigate(`/auth/messenger/${singleData.roomId}`))
    //   .catch()
  }
  // console.log(singleData.user1)
  const user1 = singleData.user1
  const user2 = singleData.user2
  const [name, setName] = useState('')
  useEffect(() => {
    if (profileId === user1.profileId) {
      setName(user1.name)
    } else {
      setName(user2.name)
    }
  }, [])
console.log(singleData)
  // const authAxios = axios.create({
  //     baseURL: API_BASE_URL,
  //     headers: {
  //         Authorization: `Bearer "${cookie.token}"`,
  //         withCredentials : true,
  //     }
  // })
  // authAxios.get(`/chat/messages/${singleData.roomId}`)
  //     .then((res) => {
  //         console.log(res)
  //         setTalkingLog(res.data);
  //     })
  //     .catch((error) => { console.log(error) });
  return (
    <TeamCarddg onClick={handleClick}>
      <Link
        to={{
          pathname: `/auth/messenger/${singleData.roomId}`,
        }}
        style={{ textDecoration: 'none',yourId: yourId }}

      >
        <TeamCardTitle> {name} </TeamCardTitle>
        <TeamCardContent>최근 메세지: {singleData.message}</TeamCardContent>
      </Link>
    </TeamCarddg>
  )
}
const TeamCarddg = styled.div`
  background-color: white;
  flex-basis: 100px;
  height: 100px;
  min-height: 100px;
  margin-left: 10px;
  margin-right: 10px;
  margin-top: 5px;
  margin-bottom: 5px;

  box-shadow: 0px 30px 40px pink;
  border-radius: 10px;
  &:hover {
    background: white;
  }
`

const TeamCardTitle = styled.h5`
  font-family: 'Cafe24Ssurround';
  color: #f46a72;
  margin-top: 10px;
  margin-left: 10px;
`

const TeamCardContent = styled.p`
    font-family : 'Cafe24SsurroundAir';
    color: grey
    margin-left: 10px;
    font-size: 15px;
`
