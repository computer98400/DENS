import React, { useState } from "react";
import styled from "styled-components";
function InputHjBox(props) {

  const { title, ph, idcheck } = props;
  
  const styleTest = {
    fontFamily: 'Roboto',
    fontStyle: 'normal',
    fontWeight: '700',
    color: '000000',  
    width: '233px',
    height: '30px',
    marginLeft: '10px',
    border:'none',
  };

  return (
    <Container {...props}>
      <Test2>{ title}</Test2>
      <Rect>
        <input placeholder={ph} style={styleTest} onChange={(e) => { props.check(e.target.value) }}></input>
      </Rect>
      <span>tests문장입니다</span>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  flex-direction: column;
`;

const Test2 = styled.span`
  font-family: Roboto;
  font-style: normal;
  font-weight: 700;
  color: rgba(131,131,131,1);
  width: 120px;
  height: 18px;
  margin-left: 4px;
`;

const Rect = styled.div`
  width: 374px;
  height: 36px;
  background-color: rgba(255,255,255,1);
  border-width: 1px;
  border-color: rgba(220,220,220,1);
  flex-direction: column;
  display: flex;
  margin-top: 5px;
  border-style: solid;
`;

export default InputHjBox;
