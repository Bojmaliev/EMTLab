
import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import {Link} from "react-router-dom";


class Login extends React.Component{
    render() {
        return (
            <Card style={{width:"100%"}}>
                <Card.Header>Login</Card.Header>
                <Card.Body>
                    <Form>
                        <Form.Group controlId="formBasicEmail">
                            <Form.Label>Email address</Form.Label>
                            <Form.Control type="email" placeholder="Enter email" />

                        </Form.Group>

                        <Form.Group controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" placeholder="Password" />
                        </Form.Group>
                        <Form.Group controlId="formBasicChecbox">
                            <Form.Check type="checkbox" label="Rembember me" />
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Login
                        </Button>
                    </Form>
                        <Link to={"/auth/forgot-password"}>Forgot your password?</Link>
                        <Link to={"/auth/register"} className={"float-right"}>Not a user?</Link>

                </Card.Body>
            </Card>

        );
    }
}

export default Login;
