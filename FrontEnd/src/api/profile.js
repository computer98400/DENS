import { apiInstance } from './index.js'

const api = apiInstance()

function profileTest(param, success, fail) {
  api.get(`/profile/${param}`).then(success).catch(fail)
}
function getKeyword(param, success, fail) {
  api.get(`/profile/keyword/${param}`).then(success).catch(fail)
}
function getImage(param, success, fail) {
  api
    .get(`/profile/image/${param}`, {
      responseType: 'blob',
    })
    .then(success)
    .catch(fail)
}
function profileUpdate([id, position, stack, git_id], success, fail) {
  api
    .put(`/profile/${id}`, { position: position, stack: stack, git_id: git_id })
    .then(success)
    .catch(fail)
}
function putKeyword([id, keyword], success, fail) {
  api
    .post(`/profile/keyword/${id}`, null, { params: { content: keyword } })
    .then(success)
    .catch(fail)
}
function ImgUpload([id, formData], success, fail) {
  api
    .post(`/profile/update/image/${id}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
    .then(success)
    .catch(fail)
}
export { profileTest, profileUpdate, putKeyword, ImgUpload, getImage, getKeyword}
