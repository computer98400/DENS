import React from 'react';
import { Outlet } from 'react-router-dom';
import HeaderBox from './HeaderBox';
import Sidebar from './sidebar';


export default function Back() {
    return (
        <>
            <HeaderBox />
            <Sidebar />
            <Outlet />
        </>
    )

}