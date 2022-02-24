import React from 'react';
import { Link, Outlet } from 'react-router-dom';
import HeaderBox from './commonComponent/HeaderBox';



export default function Day(props) {
    return (
        <>
            <h3>{ props.number }페이지입니다</h3>
            <Outlet />
        </>  

    );

}