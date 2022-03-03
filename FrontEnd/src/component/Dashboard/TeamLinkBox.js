import React from "react";
import styled from "styled-components";
import { Link, Navigate, Router } from "react-router-dom";

export default function TeamLinkBox(props) {
    // user가 URL에 직접 쳐서 들어간다면?
    // 1. Path
    //    URL/JWT로부터 가져와야
    
    
    
    return (
        <Box>
            <Link to={{
                pathname:`/auth/team/${props.team_id}`,                
            }}
            state={{
                team_id: props.team_id,
            }}
            class="text-decoration-none"
            style={{ fontFamily: 'Cafe24SsurroundAir'}}
            >
                <img src={require('./computer.PNG')} width={80} height={80}></img>
                <div>
                    <Title>{props.title}</Title> 
                </div>
            
            </Link>
        </Box>
    )
};
const Box = styled.div`
    border-radius: 5px;
    
    width: 20vh;
    height: 20vh;
    position:relative;
    left: 50%;
    transform:translate(-50%);

    display:flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    box-shadow: 3px 3px 13px rgba(244,106,114,1);
    background-color: white;

    margin-bottom: 40px;
    
`;
const Title = styled.h3`
    font-size: 1em;
    color: #f46a72;
    text-decoration-line: none;
    margin-top: 5px;`