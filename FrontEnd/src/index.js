import React from 'react'
import ReactDOM from 'react-dom'
import { BrowserRouter } from 'react-router-dom'
import App from './App'
import Testpage from './component/dashboard/test'
ReactDOM.render(<BrowserRouter><App /></BrowserRouter>, document.getElementById('root'))
// ReactDOM.render(<Testpage />, document.getElementById('root'))
