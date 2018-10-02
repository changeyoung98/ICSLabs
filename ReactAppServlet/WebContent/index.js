import React from 'react'
import { render } from 'react-dom'
import { Router, Route, hashHistory } from 'react-router'
import App from './modules/App'
import BookList from './modules/BookList'
import Login from './modules/Login'

render((
  <Router history={hashHistory}>
    <Route path="/" component={App}/>
    <Route path="/login" component={Login}/>
    <Route path="/booklist" component={BookList}/>
  </Router>
), document.getElementById('app'))
