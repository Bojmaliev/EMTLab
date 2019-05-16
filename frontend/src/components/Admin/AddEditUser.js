import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import * as React from "react";
import {fetchAllBranches} from "../repository/branchRepository";
import {adminCreateUser, adminUpdateUser} from "../repository/managerRepository";
import Alert from "react-bootstrap/Alert";


class AddEditUser extends React.Component {
    constructor(props) {
        super(props);

        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);

        this.state = {
            show: false,
            user: {
                id:null,
                name: "",
                email: "",
                password: "",
                branchId: 0,
                manager:false
            },
            branches:[],
            error:null,
            message:""
        };
        const user = this.props.existingUser;
        if(user){
            this.state.user={
                id:user.id,
                name: user.name,
                email: user.email,
                password: "",
                branchId: (user.branch? user.branch.id : 0),
                manager:(user.role === "ROLE_MANAGER")
            };
        }
        if(this.props.logged.role === "ROLE_MANAGER") this.state.branchId = this.props.logged.branch.id;
    }

    handleFormSubmit = (event)=> {
        event.preventDefault();
        if(this.props.existingUser){
            adminUpdateUser(this.state.user)
                .then(a=>a.json())
                .then(a=>{
                    let error = !(a.error === false);
                    this.setState({error: error, message:a.message});

                    if(error===false){setTimeout(this.handleClose, 500);this.props.load();}
                });
        }else{
            adminCreateUser(this.state.user)
                .then(a=>a.json())
                .then(a=>{
                    let error = !(a.error === false);

                    this.setState({error: error, message:a.message});
                    if(error===false){setTimeout(this.handleClose, 500);this.props.load();}
                });
        }
    };
    handleClose() {
        this.setState({ show: false });
    }
    handleInputChange = (event) => {
        let user = Object.assign({}, this.state.user);
        if(event.target.type === "checkbox") event.target.value = event.target.checked;
        user[event.target.name] = event.target.value;
        this.setState({user});
    };
    handleShow() {
        this.setState({ show: true });
    }
    render() {
        const { existingUser, buttonText,branches, buttonVariant} = this.props;
        const {user, error, message} = this.state;
        console.log(this.state);
        return (
            <>
                <Button variant={buttonVariant} onClick={this.handleShow}>
                    {buttonText}
                </Button>

                <Modal show={this.state.show} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>{!existingUser? "Create new user" : "Edit existing user"}</Modal.Title>
                    </Modal.Header>
                    <Form onSubmit={this.handleFormSubmit}>
                    <Modal.Body>
                        {error !== null &&
                        (error ===true ?
                            <Alert variant={"danger"}>{message}</Alert>
                            : <Alert variant={"success"}>{message}</Alert> )}


                        {[
                            {display:"Name", name:"name", placeholder:"Enter name", type:"text"},
                            {display:"Email address", name:"email", placeholder:"Enter email", type:"email"},
                            {display:"Password", name:"password", placeholder:(existingUser? "Leave empty not to edit":"Password"), type:"password"},
                        ].map((a, key)=> <Form.Group key={key} controlId={"form"+a.name}>
                            <Form.Label>{a.display}</Form.Label>
                            <Form.Control value={user[a.name]} onChange={this.handleInputChange} type={a.type} name={a.name}  placeholder={a.placeholder} />
                        </Form.Group>)}

                        {this.props.logged.role === "ROLE_ADMIN" &&
                            <><Form.Group controlId={"formBranch"}>
                                <Form.Label>Branch</Form.Label>
                                <Form.Control as={"select"} value={user.branchId} onChange={this.handleInputChange}
                                              name={"branchId"}>
                                    <option value={0}>Not set...</option>
                                    {branches.map((a, key) => <option key={key} value={a.id}>{a.name}</option>)}
                                </Form.Control>
                            </Form.Group>
                            < Form.Group controlId="formManager">
                            <Form.Check type="checkbox" checked={user.manager} onChange={this.handleInputChange}  name={"manager"} label="Manager" />
                            </Form.Group></>
                        }
                        {this.props.logged.role === "ROLE_MANAGER" &&
                        <Form.Group controlId={"formBranch"}>
                            <Form.Label>Branch</Form.Label><br/ >
                            {this.props.logged.branch.name}
                        </Form.Group>
                        }



                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.handleClose}>
                            Close
                        </Button>
                        <Button variant="primary" type={"submit"}>
                            {(existingUser ? "Save changes" : "Create user")}
                        </Button>
                    </Modal.Footer>
                    </Form>
                </Modal>
            </>
        );
    }
}

export default AddEditUser;
