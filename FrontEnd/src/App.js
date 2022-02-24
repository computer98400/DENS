import React from 'react'
import { BrowserRouter, Route, Routes, useRoutes } from 'react-router-dom'

import Signin from './component/BeforeloginComponent/Signin'
import Signup from './component/BeforeloginComponent/Signup'
import Password from './component/BeforeloginComponent/Password'

import BeforeLogin from './component/commonComponent/BeforeLogin'
import Firstpage from './component/BeforeloginComponent/Firstpage'
import Back from './component/commonComponent/Back'

import Team from './component/TeamComponent/Team'
import Teamsetting from './component/TeamComponent/Teamsetting'

import Dashboard from './component/dashboard/Dashboard'
import Messanger from './component/MessangerComponent/Messanger'

import Search from './component/SearchComponent/Search'
import Searchid from './component/SearchComponent/Searchid'

import ProfileInfo from './component/ProfileComponent/ProfileInfo'
import ProfileKeyword from './component/ProfileComponent/ProfileKeyword'
import ProfileMain from './component/ProfileComponent/ProfileMain'

import Group from './component/GroupComponent/Group'
import Groupstart from './component/GroupComponent/Groupstart'
import Groupchannel from './component/GroupComponent/Groupchannel'
import Grouplink from './component/GroupComponent/Grouplink'

import Error from './component/Error'
import Page404 from './component/Page404'
import HeaderBox from './component/commonComponent/HeaderBox'

const App = () => {
  const routes = useRoutes([
    //로그인하기전 페이지 관리
    //이전엔 상단하단에 페이지만 표시된다.
    {
      path: '/beforeLogin',
      element: <BeforeLogin />,
      children: [
        { index: true, element: <Firstpage /> },
        { path: '/beforeLogin/signin', element: <Signin /> },
        { path: '/beforeLogin/signup', element: <Signup /> },
        { path: '/beforeLogin/password', element: <Password /> },
      ],
    },
    //로그인후 페이지 관리
    //공통내용으로 header와 sidebar가 생긴다.
    {
      path: '/afterlogin',
      element: <Back />,
      children: [
        { index: true, elelment: <Dashboard /> },
        { path: '/afterlogin/dashboard', element: <Dashboard /> },
        { path: '/afterlogin/messanger', element: <Messanger /> },
        {
          path: '/afterlogin/profile',
          element: <ProfileMain />,
          children: [
            //  { index: true, element:<ProfileMain />},
            { path: '/afterlogin/profile/info', element: <ProfileInfo /> },
            {
              path: '/afterlogin/profile/keyword',
              element: <ProfileKeyword />,
            },
          ],
        },
        {
          path: '/afterlogin/team/:id',
          element: <Team number={'팀아이디 부분'} />,
          children: [
            { path: '/afterlogin/team/:id/setting', element: <Teamsetting /> },
          ],
        },
        {
          path: '/afterlogin/search',
          element: <Search />,
          children: [
            { index: true, elelment: <Search /> },
            { path: '/afterlogin/search/:teamid', element: <Searchid /> },
          ],
        },
        {
          path: '/afterlogin/group',
          element: <Group />,
          children: [
            { index: true, elelment: <Group /> },
            { path: '/afterlogin/group/start', element: <Groupstart /> },
            { path: '/afterlogin/group/channel', element: <Groupchannel /> },
            { path: '/afterlogin/group/link', element: <Grouplink /> },
          ],
        },
        {
          path: 'error',
          element: <Error />,
          children: [{ path: '404page', element: <Page404 /> }],
        },
      ],
    },
  ])

  return routes
}

export default App
