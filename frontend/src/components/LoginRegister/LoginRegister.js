import React from 'react';
import "./LoginRegister.css"
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Login from "./Login";
import {Route,BrowserRouter} from "react-router-dom";
import Register from "./Register";
import ForgotPassword from "./ForgotPassword";
import ActivateUser from "./ActivateUser";
import ChangeThePassword from "./ChangeThePassword";
class LoginRegister extends React.Component{
    render() {
        return (
            <BrowserRouter>

            <Container className="h-100">
            <Row className={"justify-content-md-center h-100"}>
                <Col xs md={6} lg={5} className={"align-middle"} style={{display:"flex", alignItems:"center", justifyContent:"center"}}>

                    <Route path={"/"} exact render={()=><Login changeUserToken={this.props.changeUserToken} />} />
                    <Route path={"/register"} component={Register} />
                    <Route path={"/forgot-password"} exact component={ForgotPassword} />
                    <Route path={"/change-password/:userId/token/:token"} component={ChangeThePassword} />
                    <Route path={"/activate-user/:userId/token/:token"} component={ActivateUser} />

                </Col>
            </Row>
            </Container>
            </BrowserRouter>
        );
    }
}

export default LoginRegister;
