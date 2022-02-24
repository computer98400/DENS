import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';


export default function Sidebar() {

    const handleClick = (e) => {
        window.location.replace("/afterlogin/dashboard");

    }

    return(
        <Container>
            <Link to="/afterlogin/dashboard"><Pos>대시보드</Pos></Link>
            <Link to="/afterlogin/team/3"><Pos>팀페이지</Pos></Link>
            <Link to="/afterlogin/search"><Pos>검색</Pos></Link>
            <Link to="/afterlogin/messanger"><Pos>메신저</Pos></Link>
        </Container>
    )
}


const Container = styled.div`
background:#F46A7243;
float:left;
height:100vh;
width:8vw;
`


const Pos = styled.button`
    border: 1px solid skyblue;

    width: 5vw;
    height: 5vh;
    background-color:rgba(0,0,0,0);
    display:inline-block;
    margin-left:1vw;
    margin-top:1vh;
`