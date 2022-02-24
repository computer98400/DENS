import { apiInstance } from './index.js';

const api = apiInstance();
//localhost:8080/test22 -> 를 가져오고싶다.
function test22(param, success, fail) {
    api.get(`/test22`,param).then(success).catch(fail);
}

function signup(param, success, fail) {
    api.post(`/signup`,param).then(success).catch(fail);
}

function login(param, success, fail) {
    api.post(`/signin`,JSON.stringify(param)).then(success).catch(fail);
}

function profileTest(param, success, fail) {
  api.get(`/profile/${param}`).then(success).catch(fail)
}
function profileUpdate({ url, data }, success, fail) {
  console.log('profile update')
  api.put(`/profile/${url}`, JSON.stringify(data)).then(success).catch(fail)
}
function dummytest(param, success, fail) {
  console.log('check keyword' + param)
  api.get(`/search/keyword/${param}`).then(success).catch(fail)
}
function dummytest2(param, success, fail) {
  api
    .get(`/search/user`, { params: { keyword: param } })
    .then(success)
    .catch(fail)
}
function dummytest3(param, success, fail) {
  console.log('check team' + param)
  api
    .get(`/search/team`, { params: { keyword: param } })
    .then(success)
    .catch(fail)
}

export {
  test22,
  signup,
  login,
  dummytest,
  dummytest2,
  dummytest3,
  profileTest,
  profileUpdate,
}
