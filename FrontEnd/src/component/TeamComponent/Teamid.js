import React from 'react';
import { Outlet, useParams } from 'react-router-dom';



export default function Searchid() {

    const { teamid } = useParams;
    console.log(teamid);
    return (
        <>
            <h2>{ teamid } 번 페이지 입니다</h2>
        </>



    )


}