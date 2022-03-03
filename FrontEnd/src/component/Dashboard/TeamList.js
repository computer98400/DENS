import React, { useEffect, useState } from "react";
import Slider from "react-slick";
import styled from "styled-components";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import TeamListBox from "./TeamListBox";
import { team } from '../../api/team';
import axios from 'axios';
import { API_BASE_URL } from '../../config';
import { useSelector } from 'react-redux';

export default function TeamList() {
    const settings = {
        dots: true,
        infinite: false,
        speed: 200,
        slidesToShow: 2,
        slidesToScroll: 2
    };

    const [info, setInfo] = useState([]);
    const token = useSelector(state => state.user.token);
    const test = localStorage.getItem("recent");
    const authAxios = axios.create({
        baseURL: API_BASE_URL,
        headers: {
            Authorization: `Bearer "${token}"`,
            withCredentials : true,
        }
    })

    const teamInfoTest = (val) => {
        // console.log(info);
        authAxios.get(`/team/showteam/${val}`)
        .then((res) => {
            // console.log(info);
            const temp = {
                title: res.data[val][0].team.title,
                content: res.data[val][0].team.content
            }
        setInfo((prevtest) => [
            ...prevtest,
            temp
        ])
    })
    .catch((err) => { console.log(err) })

    }

    useEffect(() => {
        if (!test) {
            console.log("check");
        } else {
            const check = test.split(',');
            check.map((val) => {
                teamInfoTest(val)
            })
        }
    }, []);
    return (
        <div>
          <Title>최근에 본 팀</Title>
            <Slider {...settings}>

                {info ? info.map((el, idx) => {
                    return (
                        <TeamListBox title={el.title} content={el.content} key={idx}/>
                    )})
                : <p>최근에 본 팀이 없습니다.</p> }

            </Slider>
        </div>
    );
}
const Title = styled.h3`
    font-family: Roboto;
    font-style: normal;
    font-weight: 700;
    color: rgba(244,106,114,1);
    font-family: 'Cafe24Ssurround';

`