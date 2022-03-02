import React, { useEffect, useState } from 'react';
import { detail, bringTeamMembers, editTeamContent, bringMembersImg } from '../../api/team';
import styled from "styled-components";
import { Link,useNavigate, useParams } from 'react-router-dom';
import TeamFeedContainer from './TeamFeedContainer';
import TeamMemberInfo from './TeamMemberInfo'
import MembersImg from './MembersImg';
// import {  } from "react-router-dom";
import { store } from '../..';
import { API_BASE_URL } from '../../config';
import axios from 'axios';
import { useSelector } from 'react-redux';

export default function TeamDetail(props) {
	let navigate = useNavigate();
    const [teamTitle, setTeamTitle] = useState('');
    const [teamContent, setTeamContent] = useState('');
    const [teamMembers, setTeamMembers] = useState('');
    const [checkId, setCheckId] = useState(''); // 팀장 프로필 id
    const [showInput, setShowInput] = useState(false);
	const [editInput, setEditInput] = useState('');
	const membersImgsList = [];
    const token = useSelector(state => state.user.token);
	const profileId = store.getState().user.profileid;

    const teamId = useParams().id;

	// 현재 유저가 Leader인지 판단
	const LeaderSettings = styled.div`
		${() => {
				return checkId == profileId ? "display:flex;" : "display:none;";
		}}
	`
	// console.log(checkId, profileA)?
	const authAxios = axios.create({
        baseURL: API_BASE_URL,
        headers: {
            Authorization: `Bearer "${token}"`,
            withCredentials : true,
        }
	})
	
    // 팀 명 및 팀 소개
	useEffect(() => {
		console.log(teamId);
		authAxios.get(`/team/showteam/${teamId}`)
		.then((response) => {
				console.log(response.data[0]);
				setTeamContent(response.data[teamId][0].team.content);
				setTeamTitle(response.data[teamId][0].team.title);
				setCheckId(response.data[teamId][0].user.profile.profile_id);
				// console.log(response.data[teamId][0].user.profile.profile_id);
		},)
		.catch( (error) => {
			console.log("오류가 됨.", (error));
							console.log(teamId)
		});
		
		
		// 팀원 정보 가져오기
		authAxios.get(`/teammember/${teamId}`).then((response) => { setTeamMembers(response.data); }).catch((error) => { console.log("팀원 정보 가져오기", (error)); });
	}, []);

    // 팀 소개 수정
			// Leader만 수정 버튼 활성화
		const TextBox = styled.div`
				${() => {
					return checkId == profileId ? "display : flex;" : "display: none;";
				}}
		`

		  // 글 수정 Input
		const inputChange = (e) => {
			setEditInput(e.target.value);
		}
		  // 글 수정 완료 Btn
		const editSubmit = (params) => {
			editTeamContent(
				teamId, profileId, params,
				(response) => {
					console.log(response);
					window.location.replace(`/auth/team/${teamId}`)
				},
				(error) => {
					console.log(error);
					console.log(teamId, profileId, params)
				}
			)
		}
	
	const goTeamSetting = () => {
		navigate(`/auth/team/${teamId}/settings`)
	}

    return (

    <TeamDetailBox>
			<TeamDetailHeaders>
					{/* 팀 Title */}
					<TheTeamTitle>
							{teamTitle}
					</TheTeamTitle>

					<LeaderSettings>
						{/* <Link to={{pathname:`/auth/team/${teamId}/settings`}}>
						</Link> */}
						<GotoTeamSettings onClick={() => navigate(`/auth/team/${teamId}/settings`)}>팀 설정</GotoTeamSettings>
					</LeaderSettings>
			</TeamDetailHeaders>

			<TeamDetailGrid>
					{/* 팀 피드 */}
					<TeamFeedContainer>
					</TeamFeedContainer>

					{/* 팀 소개 */}
					<TeamInfoContainer>
							<TeamInfoTitle>팀 소개</TeamInfoTitle>
							{/* 팀장/팀원 이미지 */}
							<ImgBox>
									{teamMembers ? teamMembers.map((el, key) => {
											return (
													<TeamMemberInfo name={el.name} email={el.email} key={key}/>
											)
									}) : null}

								{/* {membersImgsList}	 */}


									{/* <BringTeamMembersImg 
									src="https://w.namu.la/s/38cf17d29ddeab5a69f6de682176bbd6b8f71285f5adc1d5465c910f8d7651e8f82db2bdba9e25f1d29affdedb9ddc04edeadc4e7f539ce975eaad093a2b8c68adc73e19b58ff0f4cce679d2f2bb15e273bdcaa5e3db26bd4464e1707b67c69a"
									></BringTeamMembersImg>
									<BringTeamMembersImg 
									src="https://w.namu.la/s/38cf17d29ddeab5a69f6de682176bbd6b8f71285f5adc1d5465c910f8d7651e8f82db2bdba9e25f1d29affdedb9ddc04edeadc4e7f539ce975eaad093a2b8c68adc73e19b58ff0f4cce679d2f2bb15e273bdcaa5e3db26bd4464e1707b67c69a"
									></BringTeamMembersImg>
									<BringTeamMembersImg 
									src="https://w.namu.la/s/38cf17d29ddeab5a69f6de682176bbd6b8f71285f5adc1d5465c910f8d7651e8f82db2bdba9e25f1d29affdedb9ddc04edeadc4e7f539ce975eaad093a2b8c68adc73e19b58ff0f4cce679d2f2bb15e273bdcaa5e3db26bd4464e1707b67c69a"
									></BringTeamMembersImg>
									<BringTeamMembersImg 
									src="https://w.namu.la/s/38cf17d29ddeab5a69f6de682176bbd6b8f71285f5adc1d5465c910f8d7651e8f82db2bdba9e25f1d29affdedb9ddc04edeadc4e7f539ce975eaad093a2b8c68adc73e19b58ff0f4cce679d2f2bb15e273bdcaa5e3db26bd4464e1707b67c69a"
									></BringTeamMembersImg> */}
							</ImgBox>
							{/* 팀 Content */}
							    {/* 팀 Content */}

								<TeamInfoTextBox>

									<TextBox>
										<ContentEditBtn onClick={() => {setShowInput(!showInput)}}>수정하기</ContentEditBtn>
									</TextBox>

										{ showInput && 
											<>
												<input type="text" onChange={inputChange} />
												<ContentConfirmBtn onClick={() => {editSubmit(editInput)}}>완료</ContentConfirmBtn>
											</>
										}
									</TeamInfoTextBox>

							{/* 팀 소개 해쉬태그 */}
							<TeamInfoHashtag>
							</TeamInfoHashtag>

					</TeamInfoContainer>
			</TeamDetailGrid>
    </TeamDetailBox>

    )
}
const TeamDetailHeaders = styled.div`
	display: flex;
	justify-content: space-between;
	width: 80vw;
`
const TheTeamTitle = styled.h3`
    position: relative;
`
const GotoTeamSettings = styled.button`
	position: relative;
	background-color: #F46A72;
	border: none;
	border-radius: 2px;
	color: white;
	height: 3.5vh;
	width: 5vw;
`
const TeamDetailBox = styled.div`
	position: absolute;
	top: 48%;
	left: 50%;
	transform:translate(-50%, -50%);
`
const TeamDetailGrid = styled.div`
    // position: absolute;
    // top: 50%;
    // left: 50%;
    // transform:translate(-50%, -50%);
		margin-top: 2%;
    display: flex;
    grid-gap: 14%;
    justify-content: center;
    align-items: flex-start;
    height: 60vh;
    width: 80vw;

    // grid-template-columns: 45vw 20vw;
    // grid-template-rows: 70vh 40vh;
`

// 팀 소개
const TeamInfoContainer = styled.div`
    border: 1px solid;
    // text-align: center;
    border-radius: 5px;

    display: flex;
    flex-direction: column;
    height: 40vh;
    width: 20vw;
    overflow: scroll;

`
const TeamInfoTitle = styled.h3`
    position: relative;
    margin-top: 5%;
    left: 5%;
    font-weight: bold;
`
const BringTeamMembersImg = styled.img`
    width: 50px;
    height: 50px;
`
const ImgBox = styled.div`
    position: relative;
    left: 5%;
    margin-top: 5%;
    width:90%;
`
const TeamInfoTextBox = styled.div`
    position: relative;
    margin-top: 5%;
    left: 5%;
    width: 90%;
    word-break:break-all;
`
const TeamInfoHashtag = styled.div`
    position: relative;
    margin-top: 5%;
    left: 5%;
    width: 90%;
    word-break:break-all;
`
const ContentEditBtn = styled.button`
position: relative;
background-color: #F46A72;
border: none;
border-radius: 2px;
color: white;
height: 5.5vh;
width: 7vw;
`
const ContentConfirmBtn = styled.button`
position: relative;
background-color: #F46A72;
border: none;
border-radius: 2px;
color: white;
height: 5.5vh;
width: 7vw;
`
