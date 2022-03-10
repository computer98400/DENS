import React from 'react'
import ReactDOM from 'react-dom'
import { BrowserRouter } from 'react-router-dom'
import { createStore, applyMiddleware, compose} from 'redux'
import App from './App'
// import Testpage from './component/dashboard/test'
import { Provider } from "react-redux"
import logger from "redux-logger";
import rootReducer from "./redux/rootReducer"
import { composeWithDevTools } from "redux-devtools-extension";
const enhancer =
  process.env.NODE_ENV === "production"
    ? compose(applyMiddleware())
    : composeWithDevTools(applyMiddleware(logger));

// 위에서 만든 reducer를 스토어 만들때 넣어줍니다
const store = createStore(rootReducer, enhancer);
console.log(store.getState());
ReactDOM.render(<Provider store={store}><BrowserRouter><App /></BrowserRouter></Provider>, document.getElementById('root'))
// ReactDOM.render(<Testpage />, document.getElementById('root'))
