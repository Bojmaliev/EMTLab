import {getUserToken, SERVER_URL, urlfy} from "./functions";


export const searchClientsManager = (searchReq) =>{
    return fetch(SERVER_URL + "/manager/search-clients?"+urlfy(searchReq), {
        headers: {
            "Authorization": getUserToken()
        }
    })
};

export const adminUpdateUser = (user) =>{
    return fetch(SERVER_URL + "/manager/clients/"+user.id, {
        method: "PATCH",
        headers: {
            "Authorization": getUserToken(),
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    });
};
export const adminCreateUser = (user) =>{
    return fetch(SERVER_URL + "/manager/clients/", {
        method: "POST",
        headers: {
            "Authorization": getUserToken(),
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    });
};