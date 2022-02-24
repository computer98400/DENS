import React, { Component } from "react";
import styled, { css } from "styled-components";
import MessageTitle from "./MessageTitle"

function Message(props) {
  return (
    <Container {...props}>
      <Title>메시지</Title>
      <Rect>
        <MessageTitle></MessageTitle>
      </Rect>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 2vh;
`;

const Title = styled.span`
  font-family: Roboto;
  font-style: normal;
  font-weight: 700;
  color: rgba(244,106,114,1);
  height: 22px;
  width: 57px;
  margin-left: 3px;
`;

const Rect = styled.div`
  width: 100%;
  height: 40vh;
  margin-top: 21px;
  box-shadow: 3px 3px 5px;
`;

export default Message;
