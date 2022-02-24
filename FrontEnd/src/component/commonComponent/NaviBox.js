import React, { Component } from "react";
import styled, { css } from "styled-components";
import NavIcon from "./NavIcon";

function NaviBox(props) {
  return (
    <Container {...props}>
      <NavIcon
        style={{
          height: 73,
          width: 73,
          marginTop: 133,
          marginLeft: 24
        }}
      ></NavIcon>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  background-color: #E6E6E6;
  flex-direction: column;
`;

export default NaviBox;
