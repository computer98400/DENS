import React from 'react'
import { Outlet } from 'react-router-dom'


export default function Group() {
    return (
        <>
            <h2>Group 페이지 입니다.</h2>
            <Outlet />
        </>


    )


}