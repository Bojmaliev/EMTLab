import React from 'react';
import './App.css';
import LoginRegister from "../LoginRegister/LoginRegister";
import {BrowserRouter, Redirect, Route} from "react-router-dom";

function App() {
  return (
    <BrowserRouter>


        <Route path={"/"} exact render={()=>{
          let userKey = localStorage.getItem("userKey");
          if (!userKey) return <Redirect to={"/auth"}/>;

        }} />

        <Route path={"/auth"} component={LoginRegister}/>

    </BrowserRouter>
  );
}

export default App;
