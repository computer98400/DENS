import React from "react";
import Slider from "react-slick";
import styled from "styled-components";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import SlideBox from "./SlideBox";


export default function TeamList() {
    const settings = {
        dots: true,
        infinite: false,
        speed: 350,
        slidesToShow: 2,
        slidesToScroll: 2
      };
    return (
        <div>
          <Title>최근에 본 팀</Title>
            <Slider {...settings}>
                <SlideBox/>
                <SlideBox/>
                <SlideBox/>
                <SlideBox/>
                
            </Slider>
        </div>
    );
}
const Title = styled.h1`
    font-family: Roboto;
    font-style: normal;
    font-weight: 700;
    color: rgba(244,106,114,1);
`