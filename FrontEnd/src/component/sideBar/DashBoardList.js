import React from 'react';
import { Link, Route, Routes } from 'react-router-dom';
import styled from 'styled-components';
import DashBoard from './DashBoard';
import Test from './Test';

const brTest = styled.br`

`

function DashBoardList() {
    return (
        <>
            <ul>
                <li>
                <Link to={`/DashBoard/1`}>ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ이동1</Link>
                </li>
                <li>
                    <Link to={`/DashBoard/2`}>ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ이동2</Link>
                </li>
                <li>
                    <Link to={`/DashBoard/3`}>ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ이동3</Link>
                </li>
                <li>
                    <Link to={`/DashBoard/4`}>ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ이동4</Link>
                </li>
            </ul>
        </>
    );
}


export default DashBoardList;