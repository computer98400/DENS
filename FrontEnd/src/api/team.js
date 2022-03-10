import axios from "axios";
import { apiInstance } from "./index";

const api = apiInstance();
// 팀 만들기
function makeMyTeam(param, data, success, fail){
    api.post(`/team/create/${param}`, JSON.stringify(data)).then(success).catch(fail);
}
// 전체 팀 정보 가져오기
function team(success, fail) {
    api.get(`/team`).then(success).catch(fail);
}
// 내 팀 정보 가져오기
function myteam(param, success, fail) {
    api.get(`/team/myteam/${param}`).then(success).catch(fail);
}
// 내가 팀장인 팀 정보 가져오기
function myleadersteam(profile_id, success, fail) {
    api.get(`team/leaderteam/${profile_id}`).then(success).catch(fail);
}
// 팀의 모든 정보 가져오기
function detail(team_id, success, fail) {
    api.get(`/team/showteam/${team_id}`).then(success).catch(fail);
}
// 팀 소개 수정
function editTeamContent(team_id, profile_id, params, success, fail) {
    api.put(`/team/profile/${team_id}/${profile_id}`,
    JSON.stringify({content: params}))
    .then(success).catch(fail);
}
// 팀 멤버 가져오기
function bringTeamMembers(param, success, fail) {
    api.get(`/teammember/${param}`).then(success).catch(fail);
}
// 팀 멤버 사진 가져오기
function bringMembersImg(param, success, fail) {
    api.get(`/profile/image/${param}`, {
            responseType: 'blob',
        })
        .then(success)
        .catch(fail)
}

// Feed 불러오기(team id값)
function teamFeed(param,success, fail) {
    api.get(`/teamfeed/ourteamfeed/${param}`).then(success).catch(fail);
}
// Feed 작성
function makeTeamFeed([teamId, profile_id, formData], success, fail) {
    api.post(`teamfeed/${teamId}/${profile_id}`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    })
    .then(success)
    .catch(fail);
}
// Feed 수정
function editTeamFeed([teamfeed_id, profile_id, formData], success, fail) {
    api.put(`/teamfeed/${teamfeed_id}/${profile_id}`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    })
    .then(success)
    .catch(fail);
    console.log([teamfeed_id, profile_id, formData], success, fail)
}
// Feed 삭제
function deleteTeamFeed(teamfeed_id, profile_id, success, fail){
    api.delete(`/teamfeed/${teamfeed_id}/${profile_id}`).then(success).catch(fail);
}
// Feed 파일 다운로드
function downloadFeedFile(filename, success, fail){
    api.get(`/teamfeed/download/${filename}`).then(success).catch(fail);
}


// 팀 Settings

// 팀 멤버 방출
function dischargeMembers(team_id, profile_id, success, fail){
    api.delete(`/teammember/${team_id}/${profile_id}`).then(success).catch(fail);
}
// 팀 이름 변경
function titleChange(profile_id, team_id, param, success, fail) {
    api.put(`team/${profile_id}/${team_id}`, JSON.stringify({title: param})).then(success).catch(fail);
    console.log(param, JSON.stringify(param), JSON.stringify({title: param}))
}


// 팀 삭제
function teamBreakup(team_id, success, fail){
    api.delete(`/team/${team_id}`).then(success).catch(fail);
}
function searchMyteam(param, success, fail){
    api.delete(`/team/${param}`).then(success).catch(fail);
}

export {
    teamBreakup,searchMyteam, makeMyTeam, team, myteam, detail, teamFeed, makeTeamFeed, bringTeamMembers, titleChange, dischargeMembers, editTeamFeed,
    deleteTeamFeed, editTeamContent, bringMembersImg,downloadFeedFile, myleadersteam
}