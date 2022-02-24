import React from "react";
import InputHjBox from '../commonComponent/InputHjBox';


function Signup() {
    return (
        <>
            <h2>signup</h2>
            <h4>Before we proceed further...</h4>
            <InputHjBox title="이름" ph="이름" />
            <InputHjBox title="이메일" ph="이메일" />
            <InputHjBox title="비밀번호" ph="비밀번호" />
            <InputHjBox title="비밀번호확인" ph="비밀번호확인" />
            <button >전송하기</button>
        </>
    )
}
export default Signup;