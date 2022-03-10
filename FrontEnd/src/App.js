import React from 'react'
import {  useRoutes } from 'react-router-dom'

import Signin from './component/BeforePage/Signin'
import Signup from './component/BeforePage/Signup'
import Password from './component/BeforePage/Password'

// import Outerpage from './component/BeforePage/Outerpage'
import Back from './component/Default/Innerpage'

import TeamDetail from './component/TeamComponent/TeamDetail'
import TeamSettings from './component/TeamComponent/TeamSettings'
import CreateTeam from './component/TeamComponent/CreateTeam'
import TeamIndex from './component/TeamComponent/TeamIndex'
import TeamMain from './component/TeamComponent/TeamMain'

import Dashboard from './component/Dashboard/Main'

import Search from './component/Search/Main'

import ProfileInfo from './component/Profile/Info'
import ProfileKeyword from './component/Profile/Keyword'
import ProfileMain from './component/Profile/Main'


import Messenger from './component/Messenger/List'

import Error from './component/Error'

// import 'bootstrap/dist/css/bootstrap.min.css'
import MessageRoom from './component/Messenger/Room'
import CertiSubmit from './component/CertiSubmit'
import CertiPassword from './component/BeforePage/Passwordupdate'
import Outerpage from './component/BeforePage/Outerpage'
// import auth from './component/hoc/auth'
const App = () => {
  const routes = useRoutes([
    //로그인하기전 페이지 관리
    //이전엔 상단하단에 페이지만 표시된다.
    {
      path: '/',
      element: <Outerpage />,
      children: [
        // { index: true, element: <Firstpage /> },
        { index: true, element: <Signin /> },
        { path: '/signin', element: <Signin /> },
        { path: '/signup', element: <Signup /> },
        { path: '/password', element: <Password /> },
      ],
    },
    //로그인후 페이지 관리
    //공통내용으로 header와 sidebar가 생긴다.
    {
      path: '/auth',
      element: <Back />,
      children: [
        { index: true, element: <Dashboard /> },
        { path: '/auth/dashboard', element: <Dashboard /> },
        {
          path: '/auth/profile/:id',
          element: <ProfileMain />,
          children: [
            { path: '/auth/profile/:id/info', element: <ProfileInfo /> },
            {
              path: '/auth/profile/:id/keyword',
              element: <ProfileKeyword />,
            },
          ],
        },

        {
          path: '/auth/team',
          element: <TeamMain />,
          children: [
            { index: true, element: <TeamIndex /> },
            { path: '/auth/team/maketeam', element: <CreateTeam /> },
            { path: '/auth/team/:id', element: <TeamDetail /> },
            { path: '/auth/team/:id/settings', element: <TeamSettings /> },
          ],
        },
        {
          path: '/auth/search',
          element: <Search />,
          children: [
            { index: true, element: <Search /> },
            // { path: '/auth/search/:teamid', element: <Searchid /> },
          ],
        },
        {
          path: '/auth/messenger', element: <Messenger />,
        }, 
        { path: '/auth/messenger/:roomid', element: <MessageRoom /> },
      ],
    },
    {
      path: '*',
      element: <Error />
    },
    {
      path: '/user/password/:key', element:<CertiPassword/>
    },
    {
      path: '/certi',
      element: <CertiSubmit />
    },
  ])

  return routes
}

export default App
