import React, { useEffect, useState } from 'react';
import { Outlet } from 'react-router-dom';
import styled from 'styled-components';
import Searchbigbox from './Searchbigbox';
import dummy from '../../db/data.json';
import TeamList from './TeamList';
import UserList from './UserList';
import AllList from './AllList';
import Slider from 'react-slick';
import { searchTeamkeyword, searchUserkeyword } from '../../api/search';

// import '../../css/search.css';
export default function Search() {

    const [teamList, setTeamList] = useState([]);
    const [userList, setUserList] = useState([]);
    const [totalList, setTotalList] = useState([]);
    const [nullSearch, setNullSearch] = useState(true);
    const initList = (data) => {
        if (data === 1) {
            setTeamList([]);
        } else {
            setUserList([]);
        }
    }

    //초기화
    useEffect(() => {
        searchTeamkeyword("", (response) => {
            console.log(response);
            setTeamList(response.data)
        }, (error) => { console.log(error) });
        searchUserkeyword("", (response) => { setUserList(response.data) }, (error) => { console.log(error) });
    }, []);
    
    const searchKeyword = (e) => {
        
        setNullSearch(true);
        searchTeamkeyword(e.target.value, (response) => { setTeamList(response.data) }, (error) => { initList(1) });
        searchUserkeyword(e.target.value, (response) => { setUserList(response.data) }, (error) => { initList(2) });
       // setTotalList(...teamList, ...userList);
        //title, content, profile_id
        //teammember(사람)
        //keyword
    }
    
    const settings = {
        speed: 200,
        infinite: false,
        slidesToShow: 1,
        slidesToScroll: 1
    }

    return (
        <Container>
            <SearchInput>
                <nav class="navbar" style={{borderColor: '#f46a72'}}>
                    <div class="input-group">
                        <span class="input-group-text" style={{backgroundColor: 'transparent', borderColor: '#f46a72'}}>
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search text-danger" viewBox="0 0 16 16">
                                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                            </svg>
                        </span>
                        <input type="text" style={{color: '#000000',backgroundColor:'transparent',borderColor: '#f46a72'}} class="form-control text-light" name = 'name' onKeyUp = { searchKeyword }></input>
                    </div>
                </nav>
            </SearchInput>
            <br></br>
            {
                nullSearch ?
                    <Slider {...settings} style={{display:'flex'}}>
                    <TeamList teamlist={ teamList}/>
                    <UserList userlist={userList} />
                </Slider>
                : <AllList data={totalList} />
            }
        </Container>
        )
}

const Container = styled.div`
display:flex;
flex-direction : column;
// background-color : #fde1e36b;

`
const SearchContainer = styled.div`
    display: flex;
`
const SearchInput = styled.div`
display: flex;
justify-content:center;
align-items:center;
`
