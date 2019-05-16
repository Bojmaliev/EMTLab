import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import {Link} from "react-router-dom";
import {loginUser} from "../repository/userRepository";
import Alert from "react-bootstrap/Alert";


class Login extends React.Component{
    constructor(props) {
        super(props);
        const email = localStorage.getItem("email");
        const pass = localStorage.getItem("password");
        this.state={
            user:{
                email:(email?email:""),
                password:(pass?pass:""),
            },
            rememberMe:false,
            error:null,
            message:""

        };
    }
    handleUserChange = (event) =>{
        let user = Object.assign({}, this.state.user);
        user[event.target.name] = event.target.value;
        this.setState({user});
    };
    handleRememberChange = (event) =>{
      this.setState({rememberMe:event.target.checked});
    };
    handleLogin=(event)=> {
        event.preventDefault();
        const user = this.state.user;
        if (this.state.rememberMe) {
            localStorage.setItem("email", user.email);
            localStorage.setItem("password", user.password);
        } else {
            //remove if next time user decide not to save
            localStorage.removeItem("email");
            localStorage.removeItem("password");
        }

        //real login
        loginUser(user)
            .then(a => a.json())
            .then(a => {
                let error = !(a.error === false);
                    this.setState({error: error, message:a.message});
                if(error ===false){
                    setTimeout(()=>{
                        this.props.changeUserToken(a.tokenType+" "+a.accessToken);
                    },500);
                }
                });

    };

    render() {
        const {user,remember, error, message} = this.state;
        return (
            <Card style={{width:"100%"}}>
                <Card.Header>Login</Card.Header>
                <Card.Body>
                    {error !== null &&
                    (error ===true ?
                            <Alert variant={"danger"}>{message}</Alert>
                        : <Alert variant={"success"}>{message}</Alert> )}
                    <Form onSubmit={this.handleLogin}>
                        <Form.Group controlId="formBasicEmail">
                            <Form.Label>Email address</Form.Label>
                            <Form.Control type="email" name={"email"} value={user.email} onChange={this.handleUserChange} placeholder="Enter email" />
                        </Form.Group>

                        <Form.Group controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control name={"password"} value={user.password} onChange={this.handleUserChange} type="password" placeholder="Password" />
                        </Form.Group>

                        <Form.Group controlId="formBasicChecbox">
                            <Form.Check value={remember} onChange={this.handleRememberChange} type="checkbox" label="Rembember me" />
                        </Form.Group>

                        <Button variant="primary" type="submit">
                            Login
                        </Button>
                    </Form>
                        <Link to={"/forgot-password"}>Forgot your password?</Link>
                        <Link to={"/register"} className={"float-right"}>Not a user?</Link>

                </Card.Body>
            </Card>

        );
    }
}

export default Login;
