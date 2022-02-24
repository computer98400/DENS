import React from 'react';
import  styled  from 'styled-components';
import Searchsmallbox from './Searchsmallbox';
export default function Searchbigbox(props) {
    return (
        <>
            <Searchsmallbox info={ props.info}></Searchsmallbox>
        </>
    )
}

const Contain = styled.div`
    background-color:white;
    text-align: center;
    display:inline-block;
    width:800px;
    height:700px;
    margin-top:50px;
`