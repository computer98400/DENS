//action
const REGISTER_USER = 'REGISTER_USER';
const LOGIN_USER = 'LOGIN_USER';
const LOGOUT_USER = 'LOGOUT_USER';
const AUTH_USER = 'AUTH_USER';
const GETID = 'GETID';
const GETTOKEN = 'GETTOKEN';
const SESSIONCHECK = 'SESSIONCHECK';
//action creater
export const loginUser = (data) => ({
    type: LOGIN_USER,
    token: data.accessToken,
    profileid: data.profileid,
    auth: true,
    identity: data.Identity,
});

export const getTOKEN = () => ({type: GETTOKEN});
export const logoutUser = () => ({ type: LOGOUT_USER });

export const authUser = () => ({ type: AUTH_USER });

export const getId = () => ({ type: GETID });
export const sessionCheck = (data) => ({ type: SESSIONCHECK, token: data.token, profileid:data.myID });
//store
const initState = {
    token: '',
    auth: false,
    profileid: 0,
    identity: '',
}

//reducer
const user = (state = initState, action) => {
    switch (action.type) {
        case "LOGIN_USER":
            return {
                token: action.token,
                auth: true,
                profileid: action.profileid,
                identity: action.identity
            };        
        case "LOGOUT_USER":
            //console.log("logout");
            return {
                token: '',
                auth: false,
                profileid: 0,
                identity: ''
            };
        case "AUTH_USER":
            return state.auth;
        case "GETID":
            return state.profileid;
        case "GETTOKEN":
            return state.token;
        case "SESSIONCHECK":
            return {
                ...state,
                token: action.token,
                auth: true,
                profileid: action.profileid
            }
        default:
            return state
    }
}
export { user };