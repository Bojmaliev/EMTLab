
import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import {Link} from "react-router-dom";
import {requestNewPassword} from "../repository/userRepository";
import Alert from "react-bootstrap/Alert";



class ForgotPassword extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            email:"",
            error:null,
            message:""
        }
    }

    handleChange = (event) => {
        this.setState({email:event.target.value});
    }


    handleFormSubmit=(event) =>{
        event.preventDefault();
        requestNewPassword(this.state.email)
            .then(a=>a.json())
            .then(a=>{
                let error = !(a.error === false);
                this.setState({error: error, message:a.message});
            });
    };

    render() {
        return (
            <Card style={{width:"100%"}}>
                <Card.Header>Request a new password</Card.Header>
                <Card.Body>
                    {this.state.error !== null && <Alert variant={(this.state.error === false ? "success":"danger")}>{this.state.message}</Alert>}
                    {this.state.error !== false && <Form onSubmit={this.handleFormSubmit}>
                        <Form.Group controlId="formBasicEmail">
                            <Form.Label>Email address</Form.Label>
                            <Form.Control type="email" value={this.state.email} onChange={this.handleChange} placeholder="Enter email" />

                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Request new password
                        </Button>
                    </Form>}
                        <Link to={"/auth"}>Return to login</Link>
                </Card.Body>
            </Card>

        );
    }
}

export default ForgotPassword;
