import axios from 'axios'
import React, { useEffect } from 'react'
import { useCookies } from 'react-cookie'
import { useDispatch,  useStore } from 'react-redux'
import { useNavigate, Outlet } from 'react-router-dom'
import { apiInstance } from '../../api'
import { getMember, test11 } from '../../api/test'
import { API_BASE_URL } from '../../config'
import {
  authUser,
  getTOKEN,
  loginUser,
  sessionCheck,
} from '../../redux/userreduce'
// import Header from './HeaderBox'
import Sidebar from './Sidebar'
import Head from './Head'
import { Container, Navbar } from 'react-bootstrap'
import styled from 'styled-components';

export default function Back(props) {
  const store = useStore();

  const dispatch = useDispatch()
  const navigate = useNavigate()
  const user = store.getState();

  const token = user.user.token;
  const [cookies] = useCookies()

  useEffect(() => {
    //새로고침 시
    if (!token) {
      //쿠키에 있는지 먼저 확인
      if (cookies.token) {
        //다시 리덕스로 넣어주기.
        dispatch(sessionCheck(cookies))
        //쿠키에도 없다면
      } else {
        navigate(`/signin`)
      }
    }
  }, [])

  // console.log(dispatch());

  return(
    <>
      <Navbar style={{ backgroundColor: '#f46a72' }}>
        <Container>
          <Head />
        </Container>
      </Navbar>

      <Sidebar />
      <div className="tewst" style={{ backgroundColor: '#fde1e36b' }}>
        <Outlet />
      </div>
    </>
  )
}
const hey = document.body
hey.style.backgroundColor = '#FDE1E3';

const TheBody = styled.div`
`
const Background = styled.div`
`