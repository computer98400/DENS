import React, { useEffect, useState } from 'react';
import { Outlet } from 'react-router-dom';
import styled from 'styled-components';
import Searchbigbox from './Searchbigbox';
import dummy from '../../db/data.json';
import { dummytest,dummytest2,dummytest3 } from '../../api/test';

export default function Search() {

    const [keyword, setKeyword] = useState("");
    const [user, setUser] = useState("");
    const [team, setTeam] = useState("");
    //악시오스를 실행함.
    // const checkdummy = dummytest(keyword, (response) => { console.log(response) }, () => { console.log("check") });

    useEffect(() => {
        const fetchData = async () => {
            await dummytest(keyword, (response) => { console.log(response.data) }, () => { console.log("checkError keyWord") })
        }
        fetchData();
     }, [keyword]);
    
    useEffect(() => {
        const fetchData = async () => {
            await dummytest2(user, (response) => { console.log(response.data) }, () => { console.log("checkError user") })
        }
        fetchData();
     }, [user]);
    
    useEffect(() => {
        const fetchData = async () => {
            await dummytest3(team, (response) => { console.log(response.data) }, () => { console.log("checkError Team") })
        }
        fetchData();
     }, [team]);
    
    // useEffect(dummytest2(user, (response) => { console.log(response.data) }, () => { console.log("checkError User") }), [user]);
    
    // useEffect(dummytest3(team, (response) => { console.log(response.data) }, () => { console.log("checkError Team") }), [team]);
    return (
        <>
            <input type="text" onKeyUp={(e) => { setKeyword(e.target.value) }}></input><br></br>
            <input type="text" onKeyUp={(e) => { setUser(e.target.value) }}></input><br></br>
            <input type="text" onKeyUp={(e) => { setTeam(e.target.value) }}></input><br></br>
            <Contain>
                <Searchbigbox />
                <Searchbigbox />
                <Outlet />
            </Contain>
        </>
    )
}

const Contain = styled.div`
    background-color:white;
    text-align: center;
    flexDirection:row;
    display:inline-block;
    width:800px;
    height:700px
    margin-top:50px;
`