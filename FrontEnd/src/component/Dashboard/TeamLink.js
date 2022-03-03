import React, { useState, useEffect } from "react";
import styled, { css } from "styled-components";
import Slider from "react-slick";
import TeamLinkBox from "./TeamLinkBox";
import { myteam } from '../../api/team';
import { store } from "../..";
import axios from 'axios';
import { API_BASE_URL } from '../../config';

function TeamLink() {

    const profileId = store.getState().user.profileid;
    
    const token = store.getState().user.token;
    const [link, setLink] = useState([]);
    const [data, setData] = useState(null);
    const authAxios = axios.create({
      baseURL: API_BASE_URL,
      headers: {
          Authorization: `Bearer "${token}"`,
          withCredentials : true,
      }
    })
    
    useEffect(() => {
      authAxios.get(`/team/myteam/${profileId}`).then((response) => {
        setLink(response.data);
      }).catch();
    }, []);
    

    const settings = {
      dots: true,
      infinite: false,
      speed: 200,
      slidesToShow: 4,
      slidesToScroll: 4
    };
    const SliderStyle = {
      display: "flex",
    };
    
  return (

    <Container>

      <LinkTitle>팀 링크</LinkTitle>

      <Slider {...settings} style={SliderStyle}>

        {/* <TeamLinkBox title={"내 프로필"}>
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-hearts" viewBox="0 0 16 16">
            <path fill-rule="evenodd" d="M11.5 1.246c.832-.855 2.913.642 0 2.566-2.913-1.924-.832-3.421 0-2.566ZM9 5a3 3 0 1 1-6 0 3 3 0 0 1 6 0Zm-9 8c0 1 1 1 1 1h10s1 0 1-1-1-4-6-4-6 3-6 4Zm13.5-8.09c1.387-1.425 4.855 1.07 0 4.277-4.854-3.207-1.387-5.702 0-4.276ZM15 2.165c.555-.57 1.942.428 0 1.711-1.942-1.283-.555-2.281 0-1.71Z"/>
          </svg> 
        </TeamLinkBox> 

          {console.log(link)}*/}
          
          {link ? link.map((el, key) => {
                      return (
                          <TeamLinkBox 
                            title={el.title}
                            team_id={el.team_id}
                            key={key}/>
                      )})
          : null }
      </Slider>

    </Container>
  );
}

const Container = styled.div`
  display: flex;
  flex-direction: column;
`;

const LinkTitle = styled.h3`
  font-family : 'Cafe24Ssurround';
  font-style: normal;
  font-weight: 700;
  color: rgba(244,106,114,1);
  margin-bottom: 10px;
  // margin-left: 9px;
`;



export default TeamLink;