import * as React from "react";
import Card from "react-bootstrap/Card";
import Alert from "react-bootstrap/Alert";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {updateUserName, updateUserPassword} from "../repository/userRepository";


class EditProfile extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: props.user.name,
            password: "",
            matchPassword: "",
            errorName: null,
            errorNameMessage: "",
            errorPass: null,
            errorPassMessage: ""
        };
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({name: nextProps.user.name})
    }

    handleInputChange = (event) => {
        this.setState({[event.target.name]: event.target.value})
    };
    handleNameSubmit = (event) => {
        event.preventDefault();
        updateUserName(this.state.name)
            .then(a => a.json())
            .then(a => {
                let success = a.error === false;
                if (success)
                    this.props.fetchData();
                this.setState({errorName: !success, errorNameMessage: a.message});


            });
    };
    handlePasswordChange = (event) => {
        event.preventDefault();
        const pass = {password: this.state.password, matchPassword: this.state.matchPassword};
        updateUserPassword(pass)
            .then(a => a.json())
            .then(a => {
                let success = a.error === false;
                if (success)
                    setTimeout(() => {
                        this.props.changeUserToken(null);
                    }, 500);
                this.setState({errorPass: !success, errorPassMessage: a.message});

            });
    };

    render() {
        const {user} = this.props;
        let nameVariant = (this.state.errorName === true ? "danger" : "success");
        let passVariant = (this.state.errorPass === true ? "danger" : "success");
        return (
            <Card body>
                <h3>Edit your profile</h3>
                <Container style={{marginTop: "30px"}}>
                    <Row>
                        <Col xs={12} sm={6}>
                            {
                                this.state.errorName !== null &&
                                <Alert variant={nameVariant}>{this.state.errorNameMessage}</Alert>
                            }
                            <Form onSubmit={this.handleNameSubmit}>
                                <Form.Group controlId="formPlaintextEmail">
                                    <Form.Label>
                                        Your email
                                    </Form.Label>
                                    <Form.Control readOnly defaultValue={user.email}/>
                                </Form.Group>

                                <Form.Group controlId="formChangeName">
                                    <Form.Label>
                                        Your name
                                    </Form.Label>
                                    <Form.Control type="text" name="name" onChange={this.handleInputChange}
                                                  placeholder="Name" value={this.state.name}/>
                                </Form.Group>
                                <Button type={"submit"} variant={"primary"}>Save changes</Button>
                            </Form>
                        </Col>
                        <Col xs={12} sm={6}>
                            {
                                this.state.errorPass !== null &&
                                <Alert variant={passVariant}>{this.state.errorPassMessage}</Alert>
                            }
                            <Form onSubmit={this.handlePasswordChange}>
                                <Form.Group controlId="formChangeName">
                                    <Form.Label>
                                        Enter new password
                                    </Form.Label>
                                    <Form.Control type="password" name={"password"} onChange={this.handleInputChange}
                                                  placeholder="New password" value={this.state.password}/>
                                </Form.Group>

                                <Form.Group controlId="formChangeName">
                                    <Form.Label>
                                        Repeat new password
                                    </Form.Label>
                                    <Form.Control type="password" name={"matchPassword"}
                                                  onChange={this.handleInputChange} placeholder="Repeat password"
                                                  value={this.state.matchPassword}/>
                                </Form.Group>
                                <Button type={"submit"} variant={"secondary"}>Save new password</Button>
                            </Form>
                        </Col>
                    </Row>
                </Container>

            </Card>
        );
    }
}

export default EditProfile;