import {SERVER_URL} from "./functions";


export const activateUser = (userId, token) =>{
    return fetch(SERVER_URL+"/auth/activate/"+userId+"/token/"+token+"/");
};

export const  registerUser =(user) => {
    return fetch(SERVER_URL+"/auth/register/", {
        method:"POST",
        headers:{
            'Content-Type': "application/json"
        },
        body: JSON.stringify(user)
    });
};
export const  requestNewPassword =(email) => {
    return fetch(SERVER_URL+"/auth/forgot_password/", {
        method:"POST",
        headers:{
            'Content-Type': "application/x-www-form-urlencoded"
        },
        body: "email="+email
    });
};

export const  changePasswordWithToken =(requestBody) => {
    return fetch(SERVER_URL+"/auth/change_password/", {
        method:"POST",
        headers:{
            'Content-Type': "application/json"
        },
        body: JSON.stringify(requestBody)
    });
};