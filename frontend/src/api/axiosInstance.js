import axios from "axios";

const instance = axios.create({
  baseURL: "http://localhost:8082/api", // Replace with your backend API base URL
  headers: {
    "Content-Type": "application/json",
  },
});

export default instance;
