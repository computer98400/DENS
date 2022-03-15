import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import TeamList from './TeamList';
import UserList from './UserList';
import Slider from 'react-slick';
import { searchteam, searchUser } from '../../api/search';
import { useStore } from 'react-redux';
import { apiInstance } from '../../api';

const api = apiInstance();


export default function Search() {
    const store = useStore();
    const user = store.getState();
    const [teamList, setTeamList] = useState([]);
    const [userList, setUserList] = useState([]);
    const [totalList, setTotalList] = useState([]);
    const [nullSearch, setNullSearch] = useState(true);
    const settings = {
        speed: 200,
        infinite: false,
        slidesToShow: 1,
        slidesToScroll: 1
    }


    const initList = (data) => {
        if (data === 1) {
            setTeamList([]);
        } else {
            setUserList([]);
        }
    }
    //초기화
    useEffect(() => {
        searchteam("", (response) => {
            // console.log("hi");
            setTeamList(response.data)
        }, (error) => {
            console.log("hi")
            // console.log("check")
            console.log(error)
        });
        searchUser("", (response) => {
            // console.log(response);
            setUserList(response.data)
        }, (error) => { console.log(error) });
    }, []);
    var listTest;
    //team data in
    useEffect(() => {
        return(
            listTest = teamList.map((single) => {
                <li key={single.id}>{ single.title}</li>
            })
        )
    }, [teamList])

    //user data in
    useEffect(() => {
        // console.log("userList change");
    }, [userList])
    
    const searchKeyword = (e) => {
        setNullSearch(true);

        // api.get(`/search/team`, { params: { keyword: e.target.value } }).then();
        
        searchteam(e.target.value, (response) => {
            console.log("chekc");
            setTeamList(response.data)
        }, (error) => {
            if (error.response.status == '400') {
                initList(1)
            }
        });
        searchUser(e.target.value, (response) => { setUserList(response.data) }, (error) => { initList(2) });
    }
    return (
        <Container>
            <SearchInput>
                <nav className="navbar" style={{borderColor: '#f46a72'}}>
                    <div className="input-group">
                        <span className="input-group-text" style={{backgroundColor: 'transparent', borderColor: '#f46a72'}}>
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-search text-danger" viewBox="0 0 16 16">
                                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                            </svg>
                        </span>
                        <input type="text" style={{color: '#000000',backgroundColor:'transparent',borderColor: '#f46a72'}} className="form-control text-light" name = 'name' onKeyUp = { searchKeyword }></input>
                    </div>
                </nav>
            </SearchInput>
            <br></br>
            <div style={{width:'1300px', marginLeft:'300px'}}>
            {
                nullSearch ?
                    <Slider {...settings} style={{display:'flex'}}>
                    <TeamList teamlist={ teamList}/>
                    <UserList userlist={userList} />
                </Slider>
                : ''
                }
            </div>
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
