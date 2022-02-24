import React, { Component } from "react";
import styled, { css } from "styled-components";

function TeamLink(props) {
  const TeamData = () => [
      // {"팀 이름":"ZOI", "최근 메세지 내용":"hello", "프로필":"image", "팀원정보":"sazin"},
      // {"팀 이름":"ZOI", "최근 메세지 내용":"hello", "프로필":"image", "팀원정보":"sazin"},
      {"name":"홍길동", "age":"125"},
      {"name":"홍길동", "age":"125"},
    ]
  // console.log(TeamData)

  return (
    <Container {...props}>
      <LinkTitle>팀 링크</LinkTitle>
      <RectRow>
        
        <Rect></Rect>
        <Rect></Rect>
        <Rect></Rect>
        <Rect></Rect>
      </RectRow>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  flex-direction: column;
`;

const RectRow = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Rect = styled.div`
  width: 217px;
  height: 192px;
  box-shadow: 3px 3px 5px;
`;

const LinkTitle = styled.h3`
  font-family: Roboto;
  font-style: normal;
  font-weight: 700;
  color: rgba(244,106,114,1);
  margin-bottom: 10px;
  margin-left: 9px;
`;

export default TeamLink;
