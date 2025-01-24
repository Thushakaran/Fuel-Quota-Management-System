import axios from "axios";

const REST_API_BASE_URL = 'http://localhost:8080';

const OWNER_CASE = '/api/owner';

//function to register an owner
export const ownerregister = (ownerData) => {
    return axios.post(`${REST_API_BASE_URL}${OWNER_CASE}/register`, ownerData);
};


//function to get owner name
export const getownername = (id) => {
    return axios.get(`${REST_API_BASE_URL}${OWNER_CASE}/findName/${id}`);
  }
  
//functions to get all registered stations
export const liststations = (id) => {
  const token = localStorage.getItem("token");
  return axios.get(`${REST_API_BASE_URL}${OWNER_CASE}/findStations/${id}`, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    }
  });
}

// //function to get ownerid by login id
export const getownerid = (loginid) => {
    const token = localStorage.getItem("token");
    return axios.get(`${REST_API_BASE_URL}${OWNER_CASE}/findByLoginId/${loginid}`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      }
    });
  };
  