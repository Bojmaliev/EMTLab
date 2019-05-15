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
            <div className={"h-100 bg-primary"}>
            <Container className="h-100">
            <Row className={"justify-content-md-center h-100"}>
                <Col xs md={6} lg={5} className={"align-middle"} style={{display:"flex", alignItems:"center", justifyContent:"center"}}>

                    <Route path={"/auth"} exact component={Login} />
                    <Route path={"/auth/register"} component={Register} />
                    <Route path={"/auth/forgot-password"} exact component={ForgotPassword} />
                    <Route path={"/auth/change-password/:userId/token/:token"} component={ChangeThePassword} />
                    <Route path={"/auth/activate-user/:userId/token/:token"} component={ActivateUser} />

                </Col>
            </Row>
            </Container>
            </div>
            </BrowserRouter>
        );
    }
}

export default LoginRegister;
