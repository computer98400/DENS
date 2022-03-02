import { useEffect, useState } from 'react';
import { useCookies } from 'react-cookie';
import { useNavigate, useParams } from 'react-router-dom'
import { verify22 } from '../api/test';

export default function CertiSubmit() {
    const navigate = useNavigate();
    const [certi] = useCookies(["certi"]);
    const [point, setPoint] = useState(false);
    useEffect(() => {
        verify22(certi.certi, (response) => {
            console.log("last Test");
            console.log(response)
            setPoint(true);
        }, (error) => { console.log(error) });
    },[])

    
    const goHome = () => {
        navigate(`/signin`);
        
    }
    
    return (
        <>
            {/* { param} */}
            {!point ?<div>인증을 진행하는 중입니다.</div> : <div>인증절차가 완료되었습니다.</div>}
            {/* <button onClick={goHome}>인증완료하기</button> */}
            {/*  */}
            <button onClick={goHome}>홈으로</button>
        </>
    )
}

//회원가입 해.
//이후에 컴포넌트 이동 데이터이동은 상관없음.
//이메일로 감. 1. verifi

//이메일로 2. 