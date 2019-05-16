import Card from "react-bootstrap/Card";
import React from "react";
import Form from "react-bootstrap/Form";
import Table from "react-bootstrap/Table";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Pagination from "react-bootstrap/Pagination";
import {fetchAllBranches} from "../repository/branchRepository";
import Button from "react-bootstrap/Button";
import {searchClientsAdmin} from "../repository/adminRepository";
import {searchClientsManager} from "../repository/managerRepository";
import moment from "moment";
import AddEditUser from "./AddEditUser";
import Badge from "react-bootstrap/Badge";


class ManageEmployees extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            users: [],
            branches: [],
            searchUsers: {
                name: "",
                email: "",
                branchId: 0,
                activated: "all",
                usersPerPage: 5,
                page: 1,
            },
            numPages: 5

        };
        let timeout;
    }

    componentDidMount() {
        this.fetchClients();
        if (this.props.user.role === "ROLE_ADMIN")
            fetchAllBranches()
                .then(a => a.json())
                .then(a => this.setState({branches: a}));
    }

    handleNumberPerPageChanged = (e) => {
        let searchUsers = Object.assign({}, this.state.searchUsers);
        searchUsers.usersPerPage = e.target.value;
        this.setState({searchUsers});
        this.fetchClients();
    };
    handlePageNumberChanged = (num) => {
        let searchUsers = Object.assign({}, this.state.searchUsers);
        searchUsers.page = num;
        this.setState({searchUsers}, this.fetchClients);
    };

    handleSearchChange = (event) => {
        const name = event.target.name;
        let searchUsers = Object.assign({}, this.state.searchUsers);
        searchUsers[name] = event.target.value;
        this.setState({searchUsers}, () => {
            if(name === "email" || name === "name"){
                clearTimeout(this.timeout);
                this.timeout = setTimeout(()=> this.fetchClients(), 500);
            }
            else this.fetchClients();
        });


    };
    fetchClients = () => {
        searchClientsManager(this.state.searchUsers)
            .then(a=>a.json())
            .then(a=>{
                this.setState({users: a.clients, numPages: a.numPages});
            })
    };

    render() {
        const {users, searchUsers, branches, numPages} = this.state;
        const {user} = this.props;
        let pagItems = [];
        for (let i = 1; i <= numPages; i++) pagItems.push(<Pagination.Item key={i}
                                                                                       onClick={() => this.handlePageNumberChanged(i)}
                                                                                       active={i === searchUsers.page}>{i}</Pagination.Item>);

        return (

            <Card body>
                <h3>Manage employees</h3>
                <Container>
                    <Row>
                        <Col xs={12} className={"table-responsive"}>
                            <Table striped bordered hover>
                                <thead>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>
                                        <Form.Control name={"name"} value={searchUsers.name}
                                                      placeholder={"Enter a name to search"}
                                                      onChange={this.handleSearchChange}/>
                                    </td>
                                    <td>
                                        <Form.Control name={"email"} value={searchUsers.email}
                                                      placeholder={"Enter email to search"}
                                                      onChange={this.handleSearchChange}/>
                                    </td>
                                    <td>
                                        {user.role === "ROLE_ADMIN" &&
                                        <Form.Control as="select" name={"branchId"} value={searchUsers.branchId}
                                                      onChange={this.handleSearchChange}>
                                            <option value={0}>All branches</option>
                                            {branches.map((a, key) => <option key={key} value={a.id}>{a.name}</option>)}
                                        </Form.Control>}
                                    </td>
                                    <td>
                                        <Form.Control as="select" name={"activated"} value={searchUsers.activated}
                                                      onChange={this.handleSearchChange}>
                                            <option value={"all"}>All</option>
                                            <option value={"yes"}>Activated</option>
                                            <option value={"no"}>Not activated</option>

                                        </Form.Control>
                                    </td>
                                    <td>
                                    </td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <th>#</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Branch</th>
                                    <th>Activated</th>
                                    <th>Registered on</th>
                                    <th><AddEditUser load={this.fetchClients} logged={user} branches={branches} buttonText={"New"} buttonVariant={"primary"}/></th>
                                </tr>
                                </thead>
                                <tbody>
                                {(users.length > 0
                                    ? users.map((a, key) => <tr key={key}>
                                        <td>{a.id}</td>
                                        <td>{a.name} {a.role === "ROLE_MANAGER" && <Badge variant={"danger"}>Manager</Badge>}</td>
                                        <td>{a.email}</td>
                                        <td>{(a.branch ? a.branch.name : "Not set")}</td>
                                        <td>{(a.activated ? "Yes" : "No")}</td>
                                        <td>{moment(a.registeredOn).format('LLL')}</td>
                                        <td><AddEditUser load={this.fetchClients} logged={user} branches={branches} existingUser={a} buttonText={"Edit"} buttonVariant={"info"}/><Button variant={"danger"}>X</Button></td>

                                    </tr>)
                                    : <tr>
                                        <td colSpan={6}>No users match your filters</td>
                                    </tr>)}
                                </tbody>
                            </Table>
                        </Col>
                    </Row>
                    <Row>
                        <Col xs={4} sm={3} md={2}>
                            <Form.Control as="select" value={searchUsers.usersPerPage}
                                          onChange={this.handleNumberPerPageChanged}>
                                {[5, 10, 20, 30, 50].map((a, key) => <option key={key} value={a}>{a}</option>)}
                            </Form.Control>
                        </Col>
                        <Col xs={8} sm={9} md={10}>
                            <Pagination className={"justify-content-end"}>
                                <Pagination.Prev disabled={searchUsers.page === 1}
                                                 onClick={() => this.handlePageNumberChanged(searchUsers.page - 1)}/>
                                {pagItems}
                                <Pagination.Next disabled={searchUsers.page === numPages}
                                                 onClick={() => this.handlePageNumberChanged(searchUsers.page + 1)}/>
                            </Pagination>
                        </Col>
                    </Row>
                </Container>

            </Card>
        );
    }
}

export default ManageEmployees;