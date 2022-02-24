import React, { Component } from "react";
import styled, { css } from "styled-components";

function HeaderBox(props) {
  return (
    <Container {...props}>
      <HeaderchannelRow>
      </HeaderchannelRow>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  background-color: rgba(244,106,114,1);
  width:100vw;
`;

const HeaderchannelRow = styled.div`
  height: 82px;
  display: flex;
  flex: 1 1 0%;
  margin-right: 23px;
  margin-left: 31px;
  margin-top: 19px;
`;

export default HeaderBox;
