import React, { useState } from 'react';
import { signup, login } from '../../api/test';
import styled from 'styled-components';

export default function Signup() {


    const data = {
        email: "ehcke@testnnmse.wasera",
        name: "윤설1212",
        password: "12312123132", 
    };
    const data2 = {
        email: "ehcke@testnnmse.wasera",
        password: "12312123132", 
    };

    const data3 = {
        email: "junu@testnnmse.wasera",
        name: "윤설1212",
        password: "12312123132", 
    };
    const data32 = {
        email: "junu@testnnmse.wasera",
        password: "12312123132", 
    };
    
    const data4 = {
        email: "hj@testnnmse.wasera",
        name: "윤설1211232",
        password: "12312123132", 
    };
    
    const data42 = {
        email: "hj@testnnmse.wasera",
        password: "12312123132", 
    };


    const signupTest = () => {
        signup(data, (response) => { console.log("success check", JSON.stringify(response.data)) }, (error) => { console.log("fail Check", error) });
    }
    const signupTest2 = () => {
        signup(data3, (response) => { console.log("success check", JSON.stringify(response.data)) }, (error) => { console.log("fail Check", error) });
    }
    const signupTest3 = () => {
        signup(data4, (response) => { console.log("success check", JSON.stringify(response.data)) }, (error) => { console.log("fail Check", error) });
    }

    const signin = () => {
        login(data2, (response) => { console.log("success check22222222222", response) }, (error) => { console.log("fail Check", error) });
    }

    const signin2 = () => {
        login(data32, (response) => { console.log("success check22222222222", response) }, (error) => { console.log("fail Check", error) });
    }
    
    const signin3 = () => {
        login(data42, (response) => {
            console.log("success check22222222222", response)
            
    
        }, (error) => { console.log("fail Check", error) });
    }

    return (
        <>
            <h3>Signup 페이지입니다.</h3>
            <IconWrapper>
                <button onClick={signupTest}>ㅅㄷㄴㅅ</button>
                <button onClick={signupTest2}>ㅅㄷㄴㅅ222</button>
                <button onClick={signupTest3}>ㅅㄷㄴㅅ333</button>

                <button onClick={signin}>loginTest</button>
                <button onClick={signin2}>loginTest2222</button>
                <button onClick={signin3}>loginTest3333333</button>
                
            </IconWrapper>
        </>

    )


}

const IconWrapper = styled.div`
  display: flex;
  flex-direction: column;
`;