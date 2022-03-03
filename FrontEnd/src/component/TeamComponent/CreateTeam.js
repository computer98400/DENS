import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import styled from 'styled-components'
import { store } from '../..'
import { makeMyTeam } from '../../api/team'
import '../../css/font.css'

export default function CreateTeam() {
  const [teamName, setTeamName] = useState('')
  const [teamInfo, setTeamInfo] = useState('')

  const onChangeName = (e) => {
    setTeamName(e.target.value)
  }

  const onChangeInfo = (e) => {
    setTeamInfo(e.target.value)
  }

  const toSend = {
    title: teamName,
    content: teamInfo,
  }

  const profileId = store.getState().user.profileid

  let navigate = useNavigate()
  const TeamMaker = () => {
    // e.preventDefault()
    makeMyTeam(
      profileId,
      toSend,
      (response) => {
        console.log('response!!!!!')
        console.log(response)
        navigate(`/auth/dashboard`, { replace: true })
      },
      (error) => {
        console.log('오류가 됨.', error)
      }
    )
  }

    return (
      <div>
        <Square>
          <H3>팀 생성</H3>
          <Container>
            <Name>팀 이름</Name>
            <InputSquare type="text" onKeyUp={onChangeName} />
            <br />
            <Name>팀 소개</Name>
            <TextAreaSquare type="text" onKeyUp={onChangeInfo} />
            <Btn onClick={TeamMaker}>팀 생성</Btn>
          </Container>
        </Square>
      </div>
    )
}

const Square = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  background-color: white;
`
const MakeBoxOut = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`
const MakeBoxIn = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`
const H3 = styled.h3`
  text-align: center;
  color: #f46a72;
  font-family: 'Cafe24Ssurround';
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
  width: 30%;
  height: 3.5vh;
`
const Container = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 20px;
`
const Name = styled.span`
  font-family: 'Cafe24SsurroundAir';
  //   font-style: normal;
  //   font-weight: 700;
  width: 150px;
  height: 18px;
  margin-left: 4px;
`
const InputSquare = styled.input`
  font-family: Roboto;
  font-style: normal;
  font-weight: 700;
  margin-top: 5px;
  margin-left: 4px;
  margin-right: 4px;

  width: 374px;
  height: 36px;
  background-color: rgba(255, 255, 255, 1);
  border-width: 1px;
  border-color: rgba(220, 220, 220, 1);
  border-radius: 4px;
  display: flex;
  flex-direction: column;
  border-style: solid;
`
const TextAreaSquare = styled.input`
  font-family: Roboto;
  font-style: normal;
  font-weight: 700;
  margin-top: 5px;
  margin-left: 4px;
  margin-right: 4px;

  width: 374px;
  height: 100px;
  background-color: rgba(255, 255, 255, 1);
  border-width: 1px;
  border-color: rgba(220, 220, 220, 1);
  border-radius: 4px;
  display: flex;
  flex-direction: column;
  border-style: solid;
`
