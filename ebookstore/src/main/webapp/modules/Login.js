import React from 'react'
import { hashHistory } from 'react-router'

export default React.createClass({
	displayName: 'Login',
    
    getInitialState: function() {
      return {
        username: null,
        password: null,
        hint: null,
        isValid: false,
      };
    },

    _showHint: function(e) {
      var username = e.target.value;
      this.serverRequest = $.post("/BookStore/Hint",{q:username},function(data){
     	 this.setState({
            hint: data,
         });
      }.bind(this));
    },  

    _login: function(e) {
      e.preventDefault()
      this.state.username = e.target.elements[0].value
      this.state.password = e.target.elements[1].value
      this.serverRequest = $.post("/BookStore/checkuser",{name:this.state.username,pwd:this.state.password},function(data){
    	  this.setState({
             isValid: JSON.parse(data),
          });
    	  alert(data)
      	 if(this.state.isValid){
      		 hashHistory.push({  
      			 pathname: '/booklist',  
      		 })
      	 } 
       }.bind(this));       
    }, 
    
    _renderLoginPanel: function() {
      return (
		 <div className="toolbar">		 
		  <form onSubmit={this._login}>
		   <input type="text" name="username" id="username" onKeyUp={this._showHint}/>
           <input type="text" name="password" id="password"/>
		   <button type="submit">Login</button>
		  </form>
		  <h3>Hint: </h3><h4>{this.state.hint}</h4>
        </div>
      );
   }, 
  
  render() {
    return (
      <div>
      	{this._renderLoginPanel()}
      </div>
    );
  }
})
