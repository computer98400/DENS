import React, { Component } from "react";
import styled, { css } from "styled-components";

function MessageTitle(props) {
  return (
    <Container>
      <ColumnBox>
        <Title>팀 이름/팀장</Title>
      </ColumnBox>
      <ColumnBox>
        <Title>최근 메시지 내용</Title>
      </ColumnBox>
      <ColumnBox>
        <Title>프로필</Title>
      </ColumnBox>
      <ColumnBox>
        <Title>연결된 팀원 정보(명)</Title>
      </ColumnBox>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  flex-direction: space-around;
`;

const ColumnBox = styled.div`
`;
const Title = styled.h5`
  font-family: Roboto;
  font-style: normal;
  font-weight: 700;
  color: rgba(244,106,114,1);
  height: 22px;
  width: 88px;
`;

const 최근메시지내용 = styled.span`
  font-family: Roboto;
  font-style: normal;
  font-weight: 700;
  color: rgba(244,106,114,1);
  height: 22px;
  width: 128px;
  margin-left: 289px;
`;

const 프로필 = styled.span`
  font-family: Roboto;
  font-style: normal;
  font-weight: 700;
  color: rgba(244,106,114,1);
  height: 22px;
  width: 128px;
  margin-left: 207px;
`;

const 연결된팀원정보명 = styled.span`
  font-family: Roboto;
  font-style: normal;
  font-weight: 700;
  color: rgba(244,106,114,1);
  height: 22px;
  width: 142px;
  margin-left: 131px;
`;


export default MessageTitle;
