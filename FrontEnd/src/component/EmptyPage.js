import React from 'react';
import { Outlet } from 'react-router-dom';



export default function Day(props) {
    return (
        <>
            <h3>{props.number}페이지입니다</h3>
            <Outlet />
        </>  

    );

}