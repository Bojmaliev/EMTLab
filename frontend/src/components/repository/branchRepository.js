import {getUserToken, SERVER_URL} from "./functions";


export const fetchAllBranches = () => {
    return fetch(SERVER_URL + "/branches", {
        headers: {
            "Authorization": getUserToken()
        }
    })
};

export const addNewBranch = (name) =>{
  return fetch(SERVER_URL + "/branches",{
      method:"POST",
      headers: {
          "Authorization": getUserToken(),
          "Content-Type": "application/x-www-form-urlencoded"
      },
      body: "name=" + name
  });
};

export const updateBranchManager = (branchId, managerId) => {
    return fetch(SERVER_URL + "/branches/"+branchId+"/set-manager/"+managerId, {
        headers: {
            "Authorization": getUserToken(),
        }
    });
};