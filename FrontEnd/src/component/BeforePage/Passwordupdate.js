import { useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { certiPWD } from '../../api/test'

//비밀번호 변경 페이지
export default function CertiPassword() {
    
    const email = useRef();
    const password = useRef();
    let navigate = useNavigate();
    const sendChange = () => {
        certiPWD({email: email, password: password}, (response) => { navigate(`/signin`) }, (error) => { console.log(error) });
    }
    return (
        <>
            <h3>인증 완료되었습니다.</h3>  
            <h4>새로운 비밀번호 입력</h4>
            
            <input ref={email} ></input>
            <input ref={password}></input>
            <button onClick={sendChange}>비밀번호 변경하기</button>
        </>   




    )
}