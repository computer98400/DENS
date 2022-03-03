import React from 'react';
import { Outlet } from 'react-router-dom';
import styled from 'styled-components';


export default function Firstpage() {
    return (
        <>
            <HeadBlock>
            </HeadBlock>
            <Outlet/>
        </>
    )
}
const HeadBlock = styled.div`
    display: flex;
    background-color: rgba(244,106,114,1);
    height: 50px;
    width:100%;
    color:#fbebeb;
`
const DENSlogo = styled.img`

`