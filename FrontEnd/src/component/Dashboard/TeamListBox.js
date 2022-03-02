import React from "react";
import styled from "styled-components";

export default function TeamListBox(props) {
    return (
        <Box>
            <TopBox>
                <TextBox>
                    <TeamCardTitle>{props.title}</TeamCardTitle>
                </TextBox>
            </TopBox>
            <Rect>
                <BotBox>
                    <TeamCardContent>{props.content}</TeamCardContent>
                    {/* <BotText>#자바</BotText> */}
                </BotBox>
            </Rect>
        </Box>
    )
    
};
const TopBox = styled.div`
    height: 50%;
`
const TextBox = styled.div`
    display:flex;
    height: 100%;
    flex-direction: column;
    justify-content: center;
`

const Rect = styled.div`
    height: 50%;
`
const TopText = styled.h3`
    font-size: 1em;
    position: relative;
`
const BotBox = styled.div`
    height: 100%;
    width: 100%;
    display:grid;
    grid-template-columns: 50% 50%;
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