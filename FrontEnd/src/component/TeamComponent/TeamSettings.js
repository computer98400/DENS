import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { titleChange, teamBreakup, detail, dischargeMembers } from '../../api/team';
import styled from "styled-components";
import { useNavigate, useParams } from 'react-router-dom';
import { store } from '../..';
import '../../css/font.css'
import { ButtonToolbar } from 'react-bootstrap';

export default function TeamSettings() {

		      // profile_id, team_id 불러오기 필요
			  const myProfileId = store.getState().user.profileid;

			  const [title, setTitle] = useState('');
			  const [membersName, setMemberName] = useState('');
			  const [membersEmail, setMembersEmail] = useState('');
			  const [members, setMembers] = useState('');
		
			  const teamId = useParams().id;
		
			  const changeName = (e) => {
				 setTitle(e.target.value);
			  }
		
			  //팀 제목 수정
			  const toChangeTitle = (e) => {
				 e.preventDefault();
				 
				 setTitle(''); // 초기화
				 titleChange(
				 myProfileId, teamId, title,
				 (response) => {
					navigate(`/auth/team/${teamId}`, { replace: true })
					console.log(response);
				 },
				 (error) => {
					console.log('오류', error);
				 })
			  }
			  let navigate = useNavigate(); 
			  // 팀 삭제하기
			  const TeamDelete = (e) => {
				 teamBreakup(
					teamId,
					(response) => {
					   navigate(`/auth/dashboard`, { replace: true })
					},
					(error) => {
					   console.log("오류", error);
					}
				 )
			  }   
		
			  
			  // 팀원 멤버 정보 가져오기
			  useEffect(() => {
				 detail(
					teamId,
					(response) => {
					   setMembers(response.data[teamId]);
					   console.log(response.data)
					},
					(error) => {
					   console.log('에러', error);
					}
					)}, [])
			  
	
			  // 팀원 멤버 정보 가져오기
			  const membersInfo = () => {
				 // 팀원 방출하기
				 const outMembers = (profileId) => {
					// 팀원 방출 함수(api)
					dischargeMembers(
					   teamId, profileId,
					   (response) => {
						navigate(`/auth/team/${teamId}`, { replace: true }) //안넘어가유
						  console.log(response);
					   },
					   (error) => {
						  console.log('오류', error);
					   }
					)
				 }
		
				 const result = [];
				 for (let i=1; i < members.length; i++) {
					const profileId = members[i].user.profile.profile_id;
					console.log(profileId);
					result.push(
					 <MembersDiv key={i}>
						<div>
						{ `${members[i].user.name}` }
						</div>
						<div>
						{ `${members[i].user.email}` }
						</div>
						<Outbutton onClick={() => outMembers(profileId)}>방출하기</Outbutton>
					 </MembersDiv> 
					)
					
				 }
				 return result;
			  };
		

    return (
				<Container>
						<H3>팀 관리</H3>
		
					<hr />

					<Square>
					<H4>팀 환경설정</H4>
					<H6>팀 이름 변경</H6>
						<InputSquare type="text" onChange={changeName}/>	
						<ModifyBtn onClick={toChangeTitle}>변경</ModifyBtn>
						<H6>팀 해체</H6>
						<DeleteBtn onClick={TeamDelete}>팀 삭제하기</DeleteBtn>
					</Square>
					
					
					{/* <button onClick={toChangeTitle}>보내기</button> */}
					
					<hr />

					<Square>
					<H4>팀 멤버 관리</H4>
					{membersInfo()}
					{console.log(membersInfo)}

					{membersName ? membersName.map((el, key) => {
						return (
							<div>
								<SettingsMembers
									key={key+1}
									name={el}
								/>
							</div>
						)
					}): null}

					</Square>

					
					<hr />
					

        </Container>
    )
}
const Outbutton = styled.button `
	border-radius: 5px;
	background: #06864D;
	color: white;
	border: none;
	font-size: 15px;
	width: 15%;
	height: 9%;
`

const MembersDiv = styled.div `
	text-align: center;
	margin: auto 0;
	grid-gap: 5%;
	margin-left: 5%;
	display:flex;
	padding-left: 25%;
	padding-bottom: 5%;

`
const InputSquare = styled.input`
	width: 50%;
	text-align: center;
	margin: auto 0;
	align: center;
	
`
const DeleteBtn = styled.button `
	border-radius: 5px;
	margin: auto;
	display: block;
	background: #06864D;
	color: white;
	border: none;
	font-weight: lighter;
	font-size: 15px;
	width: 20%;
	height: 9%;
`
const ModifyBtn = styled.button `
	border-radius: 5px;
	margin: auto;
	display: block;
	background: #06864D;
	color: white;
	border: none;
	margin-top: 10px;
	margin-bottom: 50px;
	font-size: 15px;
	width: 20%;
	height: 9%;
`

const Square = styled.div `
	border-radius: 5px;	
	background-color: white;
	height: 400px;
	text-align: center;
	margin: auto 0;
`
const H6 = styled.h6 `
	color: #838383	
	margin: 10px;
	padding-top: 20px;
`
const H4 = styled.h5 `
	color: #2078BF
	margin: 20px;
	padding-top: 30px;
`
const H3 = styled.h3 `
	color: #F46A72;
	font-weight: bold;
	font-family: 'Cafe24Ssurround';
`
const Container = styled.div`

	position: absolute;
	width: 60%;
	height: 60%;

	top: 40%;
	left: 50%;
	transform:translate(-50%, -50%);
	font-family: 'Cafe24Ssurround';
`
const SettingsMembers = styled.div`
`