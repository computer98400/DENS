import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useCookies } from 'react-cookie'
import { useDispatch, useSelector } from 'react-redux'
import { Outlet } from 'react-router-dom'
import { createRooms, showRooms } from '../../api/messanger'
import { API_BASE_URL } from '../../config'
import MessangerCard from './MessangerCard'
import styled from 'styled-components'
export default function Messenger() {
  const dispatch = useDispatch()
  const token = useSelector((state) => state.user.token)
  const id = useSelector((state) => state.user.profileid)
  const [cookie] = useCookies(['token'])
  const [temp, setTemp] = useState([])
  useEffect(() => {
    const authAxios = axios.create({
      baseURL: API_BASE_URL,
      headers: {
        Authorization: `Bearer "${token}"`,
        withCredentials: true,
      },
    })
    authAxios
      .get(`/chat/rooms/${id}`)
      .then((res) => {
        // console.log(res)
        setTemp(res.data)
      })
      .catch((error) => {
        console.log(error)
      })
  }, [id])
  const createRoom = () => {
    // createRooms(1,(res) => { console.log(res) }, (error) => { console.log(error) });
  }

  return (
    <ContainerT>
      <SectionName>채팅방 목록</SectionName>
      <SearchBigBox>
        {temp
          ? temp.map((data, idx) => {
              return <MessangerCard key={idx} data={data} />
            })
          : ``}
      </SearchBigBox>
    </ContainerT>
  )
}
const ContainerT = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100vh;
`
const SectionName = styled.h3`
  font-family: 'Cafe24Ssurround';
  color: grey;
  text-shadow: 3px 3px #ccc;
  margin-top: 30px;
`
const SearchBigBox = styled.div`
  margin-left: 100px;
  display: flex;
  background-color: white;
  border: 1px solid pick;
  border-radius: 5px;
  box-shadow: 0px 30px 40px #f18fa1;
  flex-flow: column nowrap;
  width: 30vw;
  height: 76vh;
  margin: 20px;
  overflow: scroll;
  &::-webkit-scrollbar {
    display: none;
  }
`
const Btn = styled.button`
  width: 140px;
  position: relative;
  left: 50%;
  transform: translate(-50%, 0%);
  background-color: #f46a72;
  color: white;
  border: none;
  border-radius: 2px;

  margin-top: 4%;
  width: 80%;
  height: 7vh;
`
