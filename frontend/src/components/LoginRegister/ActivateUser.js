
import React from "react";

import Card from "react-bootstrap/Card";
import Alert from "react-bootstrap/Alert";
import {activateUser} from "../repository/userRepository";
import {Redirect, Link} from "react-router-dom";



class ActivateUser extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            error:null,
            message:"Loading",
        }
    };

    componentDidMount() {
        let token = this.props.match.params.token;
        let userId = this.props.match.params.userId;

        activateUser(userId, token)
            .then(a=> a.json())
            .then(a=> {
                let error = !(a.error === false);
                this.setState({error: error, message:a.message});
            });
    }


    render() {

        let variant="info";
            if(this.state.error) variant="danger";
            if(this.state.error===false) variant="success";
        return (
            <Card style={{width:"100%"}}>
                <Card.Header>Activate your user</Card.Header>
                <Card.Body>
                    <Card.Text>
                        <Alert variant={variant}>{this.state.message}</Alert>
                        {this.state.redirect && <Redirect to={"/"}/>}
                    </Card.Text>
                    <Card.Link>
                        <Link to={"/"}>Login</Link>
                    </Card.Link>
                </Card.Body>
            </Card>

        );
    }
}

export default ActivateUser;
