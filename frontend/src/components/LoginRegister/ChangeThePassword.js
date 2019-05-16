
import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import {Link} from "react-router-dom";
import {changePasswordWithToken} from "../repository/userRepository";
import Alert from "react-bootstrap/Alert";



class ChangeThePassword extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            passRequest:{
                userId:this.props.match.params.userId,
                token:this.props.match.params.token,
                password:"",
                matchPassword:""
            },
            error:null,
            message:""
        }
    }

    handleChange = (event) => {
        let passRequest = Object.assign({}, this.state.passRequest);
        passRequest[event.target.name] = event.target.value;
        this.setState({passRequest});
    };

    handleFormSubmit=(event) =>{
        event.preventDefault();
        changePasswordWithToken(this.state.passRequest)
            .then(a=>a.json())
            .then(a=>{
                let error = !(a.error === false);
                this.setState({error: error, message:a.message});
            });
    };

    render() {
        const {passRequest} = this.state;
        return (
            <Card style={{width:"100%"}}>
                <Card.Header>Request a new password</Card.Header>
                <Card.Body>
                    {this.state.error !== null && <Alert variant={(this.state.error === false ? "success":"danger")}>{this.state.message}</Alert>}
                    {this.state.error !== false && <Form onSubmit={this.handleFormSubmit}>
                        <Form.Group controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" name={"password"} value={passRequest.password} onChange={this.handleChange} placeholder="Enter new password" />
                        </Form.Group>
                        <Form.Group controlId="formBasicPasswordAgain">
                            <Form.Label>Password again</Form.Label>
                            <Form.Control type="password" name={"matchPassword"} value={passRequest.matchPassword} onChange={this.handleChange} placeholder="Repeat the new password" />
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Change your password
                        </Button>
                    </Form>}
                    <Link to={"/"}>Return to login</Link>
                </Card.Body>
            </Card>

        );
    }
}

export default ChangeThePassword;
