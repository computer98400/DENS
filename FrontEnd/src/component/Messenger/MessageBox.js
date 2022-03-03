import React, { useState, useEffect } from 'react'
import { store } from '../..'
import styled from 'styled-components'

export default function MessageBox(props) {
  console.log(props.data)
  const myID = props.profileId
  const yourId = props.yourId
  const [left, setLeft] = useState(true)
  useEffect(() => {
    if (props.data.senderId === yourId) {
      setLeft(left)
    } else {
      setLeft(!left)
    }
  }, [])

  return (
    <TeamCarddg>
      {left ? (
        <TeamCardContent>
          <TeamCardTitle>{props.data.sender}</TeamCardTitle>
          {props.data.message}
        </TeamCardContent>
      ) : (
        <TeamCardContentR>
          <TeamCardTitleR>{props.data.sender}</TeamCardTitleR>
          {props.data.message}
        </TeamCardContentR>
      )}
    </TeamCarddg>
  )
}
const TeamCarddg = styled.div`
  background-color: white;
  //   flex-basis: 70px;
  //   height: 100px;
  //   min-height: 100px;
  //   margin-left: 10px;
  //   margin-right: 10px;
  //   margin-top: 5px;
  //   margin-bottom: 5px;

  //   box-shadow: 0px 30px 40px pink;
  //   border-radius: 10px;
  //   &:hover {
  //     background: white;
  //   }
`
const TeamCardContent = styled.span`
    font-family : 'Cafe24SsurroundAir';
    color: grey
    // margin-left: 10px;
    font-size: 15px;
    display: flex;
    align-items: center;
    flex-direction: row;
    width: 35vw;
`
const TeamCardTitle = styled.h6`
  font-family: 'Cafe24Ssurround';
  color: #f46a72;
  margin-top: 10px;
  margin-left: 10px;
//   margin-right: 10px;
  display: flex;
  align-items: center;
  flex-direction: row;
`
const TeamCardContentR = styled.span`
    font-family : 'Cafe24SsurroundAir';
    width: 35vw;
    color: grey
    margin-left: 10px;
    font-size: 15px;
    display: flex;
    align-items: center;
    flex-direction: row-reverse;
`
const TeamCardTitleR = styled.h6`
  font-family: 'Cafe24Ssurround';
  color: #f46a72;
  margin-top: 10px;
  margin-left: 10px;
//   margin-right: 10px;
  display: flex;
  align-items: center;
  flex-direction: row-reverse;
`
