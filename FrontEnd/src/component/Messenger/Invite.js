import styled from "styled-components";
import Button from '../TeamComponent/Button';
import React, { useState, useEffect } from "react";
import { store } from "../..";
import { API_BASE_URL } from '../../config';
import axios from "axios";
import { useCookies } from 'react-cookie'

const TeamInviteModal = ({
  visible,
  title = '팀 초대',
  description="초대하시겠습니까?",
  confirmText = '확인',
  cancelText = '취소',
  onConfirm,
  onCancel,
  teamId,
  yourId,
}) => {

  const [cookies] = useCookies();
  const authAxios = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      Authorization: `Bearer "${cookies.token}"`,
      withCredentials: true,
    },
  })
  
  const inviteTeam = ({teamId, yourId}) => {
    authAxios.post(`/teammember/${teamId}/${yourId}`)
    .then((response) => {
        console.log(response);
    })
    .catch((fail) => {
        console.log(fail);
        console.log(teamId, yourId);
    })
}

  if (!visible) return null;
  return (

    <Fullscreen>
      <AskModalBlock>
        <h2>{title}</h2>
        <p>{description}</p>
        <button onClick={() => {inviteTeam({teamId, yourId})}}></button>
        <div className="buttons">
          <StyledButton onClick={onCancel}>{cancelText}</StyledButton>
          <StyledButton cyan onClick={onConfirm}>
            {confirmText}
          </StyledButton>
        </div>
      </AskModalBlock>
    </Fullscreen>
  );
};

export default TeamInviteModal;


const StyledButton = styled(Button)`
  height: 2rem;
  & + & {
    margin-left: 0.75rem;
  }
`;


const Fullscreen = styled.div`
  position: fixed;
  z-index: 30;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.25);
  display: flex;
  justify-content: center;
  align-items: center;
`;

const AskModalBlock = styled.div`
  width: 320px;
  background: white;
  padding: 1.5rem;
  border-radius: 4px;
  box-shadow: 0px 0px 8px rgba(0, 0, 0, 0.125);
  h2 {
    margin-top: 0;
    margin-bottom: 1rem;
  }
  p {
    margin-bottom: 3rem;
  }
  .buttons {
    display: flex;
    justify-content: flex-end;
  }
`;
const ImgInputLabel = styled.label`
  border: none;
  background-color: white;
  color: white;
  margin-left: 5%;
`
const FeedImgUpload = styled.input`
  border: none;
  background-color: white;
`
