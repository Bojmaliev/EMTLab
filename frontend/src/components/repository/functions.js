
export const SERVER_URL = "http://localhost:8080";

export const getUserToken= () =>  localStorage.getItem("userToken");

export const showRole =(role) =>{
    switch (role) {
        case "ROLE_USER": return "User";
        case "ROLE_MANAGER": return "Manager";
        case "ROLE_ADMIN": return "Admin";
        default: return "Hmm...";
    }
};
export const mapUser = (oldUser, newUser) => {
    let user = Object.assign({}, oldUser);
    user.name = newUser.name;
    user.email = newUser.username;
    user.role = newUser.authorities[0].authority;
    user.branch = newUser.branch;
    return user;
};
export function urlfy(obj) {
    return Object
        .keys(obj)
        .map(k => `${encodeURIComponent(k)}=${encodeURIComponent(obj[k])}`)
        .join('&');
}