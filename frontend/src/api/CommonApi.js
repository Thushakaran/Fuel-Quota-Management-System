import axios from "axios";

const REST_API_BASE_URL = 'http://localhost:8080';

//Function to login
export const login = (loginData) => {
  return axios.post(`${REST_API_BASE_URL}/api/auth/login`,loginData, {
    headers: {
      "Content-Type": "application/json"
    }
  });
  };