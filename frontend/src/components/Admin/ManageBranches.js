import Card from "react-bootstrap/Card";

import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";
import React from "react";
import InputGroup from "react-bootstrap/InputGroup";
import {addNewBranch, fetchAllBranches, updateBranchManager} from "../repository/branchRepository";
import {fetchFreeManagers} from "../repository/adminRepository";


class ManageBranches extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            branches: [],
            name: "",
            freeManagers: []
        };
    }

    handleBranchNameChange = (event) => {
        this.setState({name: event.target.value});
    };
    handleAddBranch = () => {
        addNewBranch(this.state.name)
            .then(a => a.json())
            .then(a => {
                if (a.error === false) {
                    this.setState({name: ""});
                    this.fetchBranches();
                } else {

                }
            });

    };

    componentDidMount() {
        this.fetchBranches();
        this.fetchFreeManagers();
    }

    fetchBranches = () => {
        fetchAllBranches()
            .then(a => a.json())
            .then(a => {
                this.setState({branches: a})
            })
    };
    fetchFreeManagers = () => {
        fetchFreeManagers()
            .then(a => a.json())
            .then(a => {
                this.setState({freeManagers: a})
            })
    };

    handleManagerChange = (branchId, managerId) => {
        updateBranchManager(branchId, managerId)
            .then(a=>{
                this.fetchBranches();
                this.fetchFreeManagers();
            });

    };

    render() {
        const {branches, name, freeManagers} = this.state;
        return (
            <Card body>
                <h3>Manage branches</h3>

                <Table striped bordered hover style={{marginTop: "30px"}}>
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Manager</th>
                        <th width="200px">
                            <InputGroup>
                                <Form.Control value={name} placeholder={"New branch name"}
                                              onChange={this.handleBranchNameChange}/>
                                <InputGroup.Append>
                                    <Button onClick={this.handleAddBranch} variant="primary">Add</Button>
                                </InputGroup.Append>
                            </InputGroup>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {(branches.length > 0 ?
                        branches.map((a, key) => <tr key={key}>
                            <td>{key + 1}</td>
                            <td>{a.name}</td>
                            <td>
                                <Form.Control value={(a.manager? a.manager.id : 0)} onChange={(event)=>this.handleManagerChange(a.id, event.target.value)} as="select">
                                    <option value={0}>None...</option>
                                    {a.manager && <option value={a.manager.id}>{a.manager.name}</option>}
                                    {freeManagers.map(b=><option value={b.id}>{b.name}</option>)}
                                </Form.Control>
                            </td>
                            <td>action</td>

                        </tr>)
                        : <tr>
                            <td colSpan={4}>No branches found</td>
                        </tr>)}
                    </tbody>
                </Table>

            </Card>
        );
    }
}

export default ManageBranches;