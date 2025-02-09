import axios from "axios";
const REST_API_BASE_URL='http://localhost:8080';
const MESSAGE_CASE='/api/notifications';

//function send notification to register an vehicle
export const sendNotificationToVehicleReg = (phoneNumber) => {
    return axios.post(`${REST_API_BASE_URL}${MESSAGE_CASE}/VehicleRegistration`, phoneNumber);
};

//function send notification to register an fuelStationOwner
export const sendNotificationToFuelStationOwnerReg = (phoneNumber) => {
    return axios.post(`${REST_API_BASE_URL}${MESSAGE_CASE}/FuelStationOwnerRegistration`, phoneNumber);
};

//function send notification to register an fuelStation
export const sendNotificationToFuelStationReg = (stationId) => {
    return axios.post(`${REST_API_BASE_URL}${MESSAGE_CASE}/FuelStationRegistration`, stationId);
};
//function send notification when pump fuel
export const sendNotificationToFuelPump = (stationId, amount) => {
    return axios.post(`${REST_API_BASE_URL}${MESSAGE_CASE}/FuelTransactionNotification`, stationId,  amount);
};
