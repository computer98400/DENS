import axios from "axios";
import { apiInstance } from "./index";

const api = apiInstance();


function initTeamList(param, success, fail){
    api.get(`/searchall팀만들주소`,param).then(success).catch(fail);
}

function initUserList(param, success, fail) {
    api.get(`searchall유저만들주소`,param).then(success).catch(fail);
}

function searchTeamkeyword(param, success, fail){
    api.get(`/search/team`, {params : {keyword:param}}).then(success).catch(fail);
}

function searchUserkeyword(param, success, fail){
    api.get(`/search/user`,  {params : {keyword:param}}).then(success).catch(fail);
}

function searchTeamID(param, success, fail){
    api.get(`/search/team/${param}`).then(success).catch(fail);
}

function searchprofileID(param, success, fail){
    api.get(`/search/user/${param}`).then(success).catch(fail);
}

function keywordCheck(param, success, fail) {
    api.get(`/profile/${param}`).then(success).catch(fail);
}


export {initTeamList,keywordCheck, initUserList,searchTeamkeyword, searchUserkeyword,searchTeamID,searchprofileID};