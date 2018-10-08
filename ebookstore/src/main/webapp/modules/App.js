import React from 'react'
import { Link } from 'react-router'

export default React.createClass({
  render() {
    return (
      <div>
        <h1>My e-Book Store</h1>
        <ul role="nav">
          <li><Link to="/login">Login</Link></li>
        </ul>
      </div>
    )
  }
})
