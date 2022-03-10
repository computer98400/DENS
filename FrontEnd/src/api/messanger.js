import { apiInstance } from '.';

const api = apiInstance();


function showRooms(param,success, fail) {
    api.get(`/chat/rooms/${param}`).then(success).catch(fail);
}
function createRooms(param,success, fail) {
    api.post(`/chat/room/${param}/7`).then(success).catch(fail);
}


export { showRooms,createRooms }