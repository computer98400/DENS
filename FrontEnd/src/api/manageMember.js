import { apiInstance } from "./index";

const api = apiInstance();

function login(param, success, fail){
    api.post(`/signin`, param).then(success).catch(fail);
}


function signup(param, success, fail){
    api.post(`/signup`, param).then(success).catch(fail);
}

export {
    login,
    signup
}