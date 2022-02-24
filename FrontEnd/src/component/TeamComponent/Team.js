import React from 'react';
import { Outlet } from 'react-router-dom';



export default function Team(props) {
    return (
        <>
            <h2>{props.id }팀페이지입니다.</h2>
            <Outlet/>
        </>


    )


}