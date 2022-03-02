import React, { useState } from 'react'
import styled from 'styled-components'
import { Image } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import { store } from '../..'
import { Navbar } from 'react-bootstrap'

export default function Head() {
  const [drop, setDrop] = useState(false)
  const userId = store.getState().user.profileid
  const clickCheck = () => {
    setDrop(!drop)
    console.log(drop)
  }

  return (
    <>
      <Navbar.Toggle />
      <img src={require('./logo.png')} style={{ width: "60px", height: "60px", border:"1px solid #FFFFFF50"}}></img>
      <Navbar.Collapse className="justify-content-end">
        <Navbar.Text>
          {/* <Image
            width={30}
            height={30}
            src={require('./profile_default.png')}
            roundedCircle
          /> */}
          <Link to={`/auth/profile/${userId}`}>
            <svg xmlns="http://www.w3.org/2000/svg" width={30} height={30} fill="currentColor" class="bi bi-person-fill text-light" viewBox="0 0 16 16">
              <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
            </svg>
          </Link>
        </Navbar.Text>
      </Navbar.Collapse>
    </>
  )
}

// const Profileimage = styled.span`
//   position: absolute;
//   background-color: rgba(244, 106, 114, 1);
//   left: 95vw;
// `

// const ProfileDrop = styled.div`
//   position: absolute;
//   background-color: rgba(244, 106, 114, 1);
//   left: 95vw;
// `
