import React from 'react';
import { Outlet } from 'react-router-dom';


export default function BeforeLogin() {
    return (
        <>
            <h2>로그인전 양식</h2>
            <Outlet/>
        </>


    )


}