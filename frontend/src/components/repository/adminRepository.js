import {SERVER_URL, getUserToken, urlfy} from "./functions";


export const fetchFreeManagers = () => {
    return fetch(SERVER_URL + "/admin/free-managers", {
        headers: {
            "Authorization": getUserToken()
        }
    })
};
