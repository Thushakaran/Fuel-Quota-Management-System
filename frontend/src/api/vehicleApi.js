import axios from 'axios';
import instance from './axiosInstance'; // Import the axios instance

// Register a new vehicle
export const registerVehicle = async (vehicleData) => {
  try {
    const response = await instance.post('/vehicles/register', vehicleData);
    return response.data; // Return the full vehicle details
  } catch (error) {
    console.error('Error registering vehicle:', error.response || error.message);
    throw error;
  }
};

export const getvehicleid = async (loginid) => {
  const token = localStorage.getItem("token");
  if (!token) {
    throw new Error("Authentication token not found. Please log in.");
  }

  return axios.get(`http://localhost:8080/api/vehicles/findbyloginid/${loginid}`, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    }
  });
};


