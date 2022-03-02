import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { teamFeed, deleteTeamFeed ,downloadFeedFile } from "../../api/team";
import { useParams } from 'react-router-dom';
import { store } from "../..";
import FeedEditModal from "./FeedEditModal";

export default function TeamFeedIndex() {

	const teamId = useParams().id;
	const [feedIndex, setFeedIndex] = useState([]);
	const profileId = store.getState().user.profileid;

  // 피드 정보 가져오기
	// 피드 이미지 출력
	const [feedBase64, setFeedBase64] = useState([])
	let feedReader = new FileReader();
  useEffect(() => {
    teamFeed(teamId,
      (response) => {
				setFeedIndex(response.data);
				let url = response.data[0].teamFeedFiles[0];
				feedReader.readAsDataURL(url);
				// feedReader.readAsDataURL(response.data[0].teamFeedFiles[0]);
				feedReader.onloadend = () => {
					const indexBase64 = feedReader.result;
					console.log(indexBase64);
					if (indexBase64) {
						let indexBase64Sub = indexBase64.toString();
						setFeedBase64(feedBase64 => [...feedBase64, indexBase64Sub])
					}
				}
      },
      (error) => {
          console.log("오류가 됨.", (error));
      }); 
  }, []);

	// 피드 삭제
	const feedDelete = (teamFeedId) => {
		deleteTeamFeed(
			teamFeedId, profileId,
			(response) => {
				console.log(response);
			},
			(error) => {
				console.log(error);
			}
		)
	}

	// Index 렌더링
	const [modal, setModal] = useState(false);
	const [editFeedId, setEditFeedId] = useState('');
	const modalOn = (teamFeedId) => {
		setModal(true);
		setEditFeedId(teamFeedId);
	};
	const onCancel = () => {
    setModal(false);
  };
  const onConfirm = () => {
    setModal(false);
  };

	const membersInfo = () => {
		const result = [];

		for (let i=0; i < feedIndex.length; i++) {
			let teamFeedId = feedIndex[i].teamfeed_id;
			const WriterBtn = styled.div`
				${(  ) => {
					return feedIndex[i].writer === profileId ? 'display: flex' : 'display: none';
				}}
			`

			result.push(
				 <Feed key={i}>
					 <FeedContent>
						<div>
							{ `${feedIndex[i].content}` }
						</div>
					 </FeedContent>
						
						<WriterBtn>
							<EditBtn onClick={() => {modalOn(teamFeedId)}} id="EditBtn">✏️</EditBtn>
							<DeleteBtn onClick={() => {feedDelete(teamFeedId)}}>🗑️</DeleteBtn>
						</WriterBtn>
					
				 </Feed>
			)
		}
		return result;
	};
	
	return (
		<IndexContainer>
					{membersInfo()}
					<FeedEditModal 
						visible={modal}
						onConfirm={onConfirm}
						onCancel={onCancel}
						teamId={teamId}
						editFeedId={editFeedId}
					/>
        </IndexContainer>
    )
};

const IndexContainer = styled.div`
    position: relative;
    margin-top: 5%;
    left: 5%;
    width: 90%;
`
const Feed = styled.div`
		display: flex;
		justify-content: space-between;
    margin-top: 1vh;
    width: 100%;
    word-break:break-all;
		border-bottom: 1px solid #aaa;
`
const FeedContent = styled.div`
	display: flex;
	flex-direction: column;
	margin-top: 4%;
`
const EditBtn = styled.button`
	border: none;
	background-color: transparent;
`
const DeleteBtn = styled.button`
	border: none;
	background-color: transparent;

`
