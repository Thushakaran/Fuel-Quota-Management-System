import "./App.css";
import VehicleRegistration from "./components/VehicleRegistration";
import FuelStationRegistration from "./RegisterFuelStation/FuelStationRegistration"
import {BrowserRouter, Routes, Route} from 'react-router-dom'
import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import AboutUsPage from "./pages/AboutUsPage";

function App() {

  return (
    <>
      
      <BrowserRouter>
      <Routes> 
        <Route path="/register" element={<VehicleRegistration />}></Route>
        <Route path="/stationregister" element={<FuelStationRegistration/>}></Route>
        <Route path="" element={<HomePage/>}></Route>
        <Route path="/login" element={<LoginPage/>}></Route>
        <Route path="/about" element={<AboutUsPage/>}></Route>
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
