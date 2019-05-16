import React from 'react';
import './App.css';
import LoginRegister from "../LoginRegister/LoginRegister";
import {Route, withRouter} from "react-router-dom";
import Dashboard from "../Dashboard/Dashboard";

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userToken: localStorage.getItem("userToken")
        }
    }

    changeUserToken = (token) => {
        if (token)
            localStorage.setItem("userToken", token);
        else {
            this.props.history.push("/");
            localStorage.removeItem("userToken")
        }
        this.setState({userToken: token});
    };

    render() {
                const {userToken} = this.state;
                return (
                <div className={"h-100 p-5"} style={{background: "rgb(100,100,100)"}}>
                <Route path={"/"} render={() => {
                    if (!userToken)
                        return <LoginRegister changeUserToken={this.changeUserToken}/>;
                    else
                        return <Dashboard changeUserToken={this.changeUserToken}/>;
                }}/>

            </div>
        );
    }
}

export default withRouter(App);
