import styled from "styled-components";
import Button from './Button';
import React, { useState, useEffect } from "react";
import { store } from "../..";
import { editTeamFeed } from "../../api/team";


const FeedEditModal = ({
  visible,
  title = '피드 수정',
  description="수정할 내용",
  confirmText = '확인',
  cancelText = '취소',
  onConfirm,
  onCancel,
  teamId,
  editFeedId
}) => {
  const [childContent, setChildContent] = useState('');
  let [editImg, setEditImg] = useState();
  let [editFile, setEditFile] = useState();
	const profileId = store.getState().user.profileid;
  const [imgName, setImgName] = useState('');
  const [fileName, setFileName] = useState('');

  const handleInput = (e) => {
    setChildContent(e.target.value);
  }
	
  // 수정 완료 button
  const formData = new FormData();
	const submitContent = ({editFeedId}) => {
    if (!editImg) {
      editImg = new File([""], "filename");
      console.log(11);
    }
    if (!editFile) {
      editFile = new File([""], "filename");
    }

    formData.append("content", childContent);
    formData.append("imageFiles", editImg);
    formData.append("generalFiles", editFile);

		editTeamFeed(
			[editFeedId, profileId, formData],
			(response) => {
				console.log(response);
				console.log([editFeedId, profileId, formData]);
				window.location.replace(`/auth/team/${teamId}`);
			},
			(error) => {
				console.log(error);
				console.log([editFeedId, profileId, formData]);
				console.log(childContent, editImg, editFile);
        for(var pair of formData.entries()) {
          console.log(pair[0]+ ', '+ pair[1]); 
       }
			}
		)
	}

  if (!visible) return null;
  return (

    <Fullscreen>

      <AskModalBlock>
        <h2>{title}</h2>
        <p>{description}</p>
        <input type="text" onChange={handleInput} />
        <button onClick={() => {submitContent({editFeedId})}}>확인</button>



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

export default FeedEditModal;


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
