import {SERVER_URL, getUserToken} from "./functions";


export const activateUser = (userId, token) => {
    return fetch(SERVER_URL + "/auth/activate/" + userId + "/token/" + token + "/");
};

export const registerUser = (user) => {
    return fetch(SERVER_URL + "/auth/register/", {
        method: "POST",
        headers: {
            'Content-Type': "application/json"
        },
        body: JSON.stringify(user)
    });
};
export const requestNewPassword = (email) => {
    return fetch(SERVER_URL + "/auth/forgot_password/", {
        method: "POST",
        headers: {
            'Content-Type': "application/x-www-form-urlencoded"
        },
        body: "email=" + email
    });
};

export const changePasswordWithToken = (requestBody) => {
    return fetch(SERVER_URL + "/auth/change_password/", {
        method: "POST",
        headers: {
            'Content-Type': "application/json"
        },
        body: JSON.stringify(requestBody)
    });
};

export const loginUser = (user) => {
    return fetch(SERVER_URL + "/auth/login/", {
        method: "POST",
        headers: {
            'Content-Type': "application/json"
        },
        body: JSON.stringify(user)
    });
};

export const getMyInfo = () => {
    return fetch(SERVER_URL + "/users/me/", {
        headers: {
            "Authorization": getUserToken()
        }
    })
};
export const getMyColleagues = () => {
    return fetch(SERVER_URL + "/users/me/colleagues", {
        headers: {
            "Authorization": getUserToken()
        }
    })
};
export const updateUserName = (name) => {
    return fetch(SERVER_URL + "/users/me/name", {
        method: "PATCH",
        headers: {
            "Authorization": getUserToken(),
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "name=" + name
    })
};
export const updateUserPassword = (passwordRequest) => {
    return fetch(SERVER_URL + "/users/me/password", {
        method: "PATCH",
        headers: {
            "Authorization": getUserToken(),
            "Content-Type": "application/json"
        },
        body: JSON.stringify(passwordRequest)
    })
};
