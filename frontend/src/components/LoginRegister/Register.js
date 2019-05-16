import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import {Link} from "react-router-dom";
import Alert from "react-bootstrap/Alert";
import {registerUser} from "../repository/userRepository";

class Register extends React.Component{
    constructor(props) {
        super(props);
        this.state= {
            user:{
                name:"",
                email:"",
                password:"",
                matchPassword:""
            },
            error:null,
            message:""
        }
    };

    handleInputChange = (event) => {
        let user = Object.assign({}, this.state.user);
        user[event.target.name] = event.target.value;
        this.setState({user});
    };

    handleFormSubmit= (event) => {
      event.preventDefault();
        registerUser(this.state.user)
            .then(a=> a.json())
            .then(a=>{
                let error = !(a.error === false);
                this.setState({error: error, message:a.message});
            })
    };

    render() {
        const {user} = this.state;
        return (
            <Card style={{width:"100%"}}>
                <Card.Header>Register</Card.Header>
                <Card.Body>
                    {this.state.error !== null && <Alert variant={(this.state.error === false ? "success":"danger")}>{this.state.message}</Alert>}
                    {this.state.error !== false &&
                    <Form onSubmit={this.handleFormSubmit}>
                        {[
                            {display:"Your name", name:"name", placeholder:"Enter your name", type:"text"},
                            {display:"Email address", name:"email", placeholder:"Enter email", type:"email", muted:"We'll never share your email with anyone else."},
                            {display:"Password", name:"password", placeholder:"Password", type:"password"},
                            {display:"Repeat password", name:"matchPassword", placeholder:"Enter your password again", type:"password"},
                        ].map((a, key)=> <Form.Group key={key} controlId={"form"+a.name}>
                            <Form.Label>{a.display}</Form.Label>
                            <Form.Control value={user[a.name]} onChange={this.handleInputChange} type={a.type} name={a.name}  placeholder={a.placeholder} />
                            {a.muted && <Form.Text className="text-muted">
                                {a.muted}
                            </Form.Text>}
                        </Form.Group>)}

                        <Button variant="primary" type="submit">
                            Register
                        </Button>
                    </Form>}
                    <Link to={"/"}>Return to login</Link>
                </Card.Body>
            </Card>

        );
    }
}

export default Register;
