import React from 'react';
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import {BrowserRouter, Link, Route} from "react-router-dom";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import NavDropdown from "react-bootstrap/NavDropdown";
import Form from "react-bootstrap/Form";
import FormControl from "react-bootstrap/es/FormControl";
import Button from "react-bootstrap/Button";
import {getMyColleagues, getMyInfo} from "../repository/userRepository";
import {mapUser, showRole} from "../repository/functions";
import Badge from "react-bootstrap/Badge";
import Alert from "react-bootstrap/Alert";
import Card from "react-bootstrap/Card";
import EditProfile from "./EditProfile";
import Table from "react-bootstrap/Table";
import ManageBranches from "../Admin/ManageBranches";
import ManageEmployees from "../Admin/ManageEmployees";


class Dashboard extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: {
                name: "",
                branch: null,
                email: "",
                role: ""
            },
            colleagues: []
        };

    }

    componentDidMount() {
        this.fetchData();
     }

    fetchData = () => {
        getMyInfo()
            .then(a => a.json())
            .then(a => {
                if (a.error) this.props.changeUserToken(null);
                this.setState({user: mapUser(this.state.user, a)});
            });
        if (this.state.user.role === "ROLE_USER")
            getMyColleagues()
                .then(a => a.json())
                .then(a => {
                    this.setState({colleagues: a});
                });
    };

    render() {
        const {user, colleagues} = this.state;
        return (
            <BrowserRouter>
                <Container className="h-75 p-0">
                    <Navbar bg={"light"} expand="lg">
                        <Navbar.Brand as={Link} to="/">EMT Laboratory</Navbar.Brand>
                        <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                        <Navbar.Collapse id="basic-navbar-nav">
                            <Nav className="mr-auto">
                                <Nav.Link as={Link} to="/edit-profile">Edit profile</Nav.Link>
                                {
                                    user.role === "ROLE_MANAGER" &&
                                    <NavDropdown title="Manager" id="basic-nav-dropdown">
                                        <NavDropdown.Item as={Link} to={"/manage-employees"}>Manage
                                            employees</NavDropdown.Item>
                                    </NavDropdown>
                                }
                                {
                                    user.role === "ROLE_ADMIN" &&
                                    <NavDropdown title="Admin" id="basic-nav-dropdown">
                                        <NavDropdown.Item as={Link} to={"/manage-employees"}>Manage
                                            employees</NavDropdown.Item>
                                        <NavDropdown.Item as={Link} to={"/manage-branches"}>Manage
                                            branches</NavDropdown.Item>
                                    </NavDropdown>
                                }


                                <Nav.Link onClick={() => this.props.changeUserToken(null)}>Logout</Nav.Link>
                            </Nav>
                        </Navbar.Collapse>
                    </Navbar>
                    <br/>
                    <Row>
                        <Col xs={12}>

                            <Route path={"/"} exact render={() =>
                                <Card body>
                                    <h1>Wecome, <Badge variant={"light"}>{user.name}</Badge></h1>
                                    <p>
                                        You see this page because you are logged in the <Badge variant={"dark"}>EMT
                                        lab</Badge>
                                    </p>
                                    <p>
                                        Your current role is: <Badge variant="success">{showRole(user.role)}</Badge>
                                    </p>
                                    <p>
                                        {user.role === "ROLE_USER" && (user.branch
                                            ? "You currently work in " +
                                            <Badge variant={"info"}>{user.branch.name}</Badge>
                                            : <Alert variant={"info"}>You currently don't have a branch!</Alert>)}
                                    </p>
                                    {user.role === "ROLE_USER" &&
                                    <Table striped bordered hover>
                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Name</th>
                                            <th>Email</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        {(colleagues.length > 0 ? colleagues.map((a, key) => <tr key={key}>
                                            <td>{key}</td>
                                            <td>{a.name}</td>
                                            <td>{a.email}</td>

                                        </tr>) : <tr>
                                            <td colSpan={3}>You dont have colleagues</td>
                                        </tr>)}
                                        </tbody>
                                    </Table>

                                    }
                                </Card>
                            }/>

                            <Route path={"/edit-profile"}
                                   render={() => <EditProfile changeUserToken={this.props.changeUserToken}
                                                              fetchData={this.fetchData} user={user}/>}/>

                            {user.role === "ROLE_ADMIN" &&
                            <Route path={"/manage-branches"} render={() => <ManageBranches/>}/>}

                            {(user.role === "ROLE_ADMIN" || user.role === "ROLE_MANAGER") &&
                            <Route path={"/manage-employees"} render={() => <ManageEmployees user={user}/>}/>}

                        </Col>
                    </Row>
                </Container>
            </BrowserRouter>
        );
    }
}

export default Dashboard;
