import React from 'react';
import { Link, useParams } from 'react-router-dom';
import styled from 'styled-components';

const DashBoard = (props) => {
    console.log(props.number);
    return (
        <>
            <Link to={`/DashBoard`}>asdasdfasdfafsdfasdfasdasfdfasdasdfasfdfasdsfad로비로이동</Link>
            <Test>asdfsdfaadfsasdfasdfasdfasdfadfsadfs</Test>
        </>
    )
}

const Test = styled.p`
left:400px;
margin-left:200px;
`
const Stest = styled.span`
left:400px;
margin-left:200px;
`


export default DashBoard;