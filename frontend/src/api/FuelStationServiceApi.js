import axios from "axios";

const REST_API_BASE_URL = 'http://localhost:8080';
const FUEL_STATION_BASE = '/api/fuel-station';


// Function to register a fuel station
export const fuelstationregister = (fuelStationData) => {
  return axios.post(`${REST_API_BASE_URL}${FUEL_STATION_BASE}/register`, fuelStationData);
};

// //function to get ownerid by login id
export const getstationid = (loginid) => {
  const token = localStorage.getItem("token");
  return axios.get(`${REST_API_BASE_URL}${FUEL_STATION_BASE}/findByLoginId/${loginid}`, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    }
  });
};


// function to get station name 
export const getstationname = (stationId) => {
  const token = localStorage.getItem("token");
  return axios.get(`${REST_API_BASE_URL}${FUEL_STATION_BASE}/findName/${stationId}`, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    }
  });
}

//function to get fuelTypes
export const getfuelInventory = (stationId) => {
  const token = localStorage.getItem("token");
  return axios.get(`${REST_API_BASE_URL}${FUEL_STATION_BASE}/findFuels/${stationId}`, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    }
  });

};



export const addFuel = (stationId, fuels) => {
  const token = localStorage.getItem('token');
  return axios.post(
    `${REST_API_BASE_URL}${FUEL_STATION_BASE}/addFuel/${stationId}`,
    fuels, // Map { fuelType: quantity }
    {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

export const editStationDetails  = (stationId,station) => {
 const token = localStorage.getItem("token");
 return axios.put(`${REST_API_BASE_URL}${FUEL_STATION_BASE}/saveDetails/${stationId}`,station, {
   headers: {
     "Content-Type": "application/json",
     Authorization: `Bearer ${token}`,
   }
 });

};

export const getStationDetailById  = (stationId) => {
 const token = localStorage.getItem("token");
 return axios.get(`${REST_API_BASE_URL}${FUEL_STATION_BASE}/findDetail/${stationId}`, {
   headers: {
     "Content-Type": "application/json",
     Authorization: `Bearer ${token}`,
   }
 });

};


//

