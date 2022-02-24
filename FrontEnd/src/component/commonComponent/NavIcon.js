import React, { Component } from "react";
import styled, { css } from "styled-components";

function NavIcon(props) {
  return (
    <Container {...props}>
      <svg
        viewBox="0 0 73 73"
        style={{
          width: 73,
          height: 73
        }}
      >
        <ellipse
          stroke="rgba(230, 230, 230,1)"
          strokeWidth={0}
          fill="rgba(244,251,1,1)"
          cx={37}
          cy={37}
          rx={37}
          ry={37}
        ></ellipse>
      </svg>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  flex-direction: column;
`;

export default NavIcon;
