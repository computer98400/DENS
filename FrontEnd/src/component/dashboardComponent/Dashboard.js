import React from "react";
import styled from "styled-components";
import TeamLink from "./TeamLink";
import Message from "./Message";
import TeamList from "./TeamList"

export default function Dashboard() {
    return (
      <DashBoardBox>

        <TeamLink></TeamLink>

        <MessageBox>
          <Message></Message>

        </MessageBox>

        {/* 캐러셀 */}
        <TeamList/> 

      </DashBoardBox>
    );
  }
  const DashBoardBox = styled.div`
    position: absolute;
    top: 17%;
    left: 50%;
    transform:translate(-50%, -50%);
    height: 215px;
    width: 1387px;
    // marginTop: 103px;
    // marginLeft: 293px;
  `
  const 팀이름팀장 = styled.span`
    font-family: Roboto;
    top: 78px;
    left: 133px;
    position: absolute;
    font-style: normal;
    font-weight: 700;
    color: rgba(244,106,114,1);
    height: 22px;
    width: 88px;
  `;
  
  const MessageBox = styled.div`
  `;
  
  const 최근내가본팀 = styled.span`
    font-family: Roboto;
    font-style: normal;
    font-weight: 700;
    color: rgba(244,106,114,1);
    height: 22px;
    width: 100px;
    margin-top: 40px;
    margin-left: 301px;
  `;
  
  const Rect5 = styled.div`
    width: 569px;
    height: 137px;
    background-color: #E6E6E6;
  `;
  
  const Rect6 = styled.div`
    width: 569px;
    height: 137px;
    background-color: #E6E6E6;
    margin-left: 32px;
  `;
  
  const Rect5Row = styled.div`
    height: 137px;
    flex-direction: row;
    display: flex;
    margin-top: 60px;
    margin-left: 359px;
    margin-right: 391px;
  `;