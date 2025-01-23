import axios from "axios";

const REST_API_BASE_URL = 'http://localhost:8082';

const OWNER_CASE = '/api/owner';

// Function to register an owner
export const ownerregister = (ownerData) => {
    return axios.post(`${REST_API_BASE_URL}${OWNER_CASE}/register`, ownerData);
};


// function to get owner name
export const getownername = (id) => {
    return axios.get(`${REST_API_BASE_URL}${OWNER_CASE}/findname/${id}`);
  }
  
// functions to get all registered stations
export const liststations = (id) => {
  return axios.get(`${REST_API_BASE_URL}${OWNER_CASE}/findstations/${id}`);
}

// //function to get ownerid by login id
export const getownerid = (loginid) => {
    const token = localStorage.getItem("token");
    return axios.get(`${REST_API_BASE_URL}${OWNER_CASE}/findbyloginid/${loginid}`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      }
    });
  };
  