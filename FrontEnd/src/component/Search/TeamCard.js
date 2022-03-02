import React from "react";
import styled from "styled-components";
import { searchTeamID } from "../../api/search";

export default function TeamCard(props){
   // console.log(props.data);
    //console.log(props.check);
    const check = () => {
        props.click(props.data.team_id,props.data.title,props.data.content);
        searchTeamID(props.check,(response) => {console.log(response.data)}, (error)=> {console.log(error)});
    }

    return(
        <TeamCarddg onClick={check}>
            <TeamCardTitle>팀 이름 : {props.data.title}</TeamCardTitle>
            <TeamCardContent>{props.data.content}</TeamCardContent>
        </TeamCarddg>
    )
}
const TeamCarddg = styled.div`
    background-color: white;
    flex-basis: 100px;
    height: 100px;
    min-height: 100px;
    margin-left: 10px;
    margin-right: 10px;
    margin-top: 5px;
    margin-bottom: 5px;

    box-shadow : 0px 30px 40px pink;
    border-radius: 10px;
    &:hover{
        background: white;
    }
`

const TeamCardTitle = styled.h5`
    font-family : 'Cafe24Ssurround';
    color: #F46A72;
    margin-top: 10px;
    margin-left: 10px;
`

const TeamCardContent = styled.p`
    font-family : 'Cafe24SsurroundAir';
    color: grey
    margin-left: 10px;
    font-size: 15px;
`
