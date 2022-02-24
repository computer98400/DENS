import React from 'react'
import { Routes, Route } from 'react-router-dom'
import { DashBoardList, DashBoard, Messenger, Profile } from './index'

const Router = () => {
  return (
    <>
      <Route path="/DashBoard" component={DashBoardList}></Route>
      <Route path="/Messenger" component={Messenger}></Route>
      <Route path="/Profile" component={Profile}></Route>
    </>
  )
}

export default Router
