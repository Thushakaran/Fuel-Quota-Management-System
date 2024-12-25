
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
