import VehicleRegistration from "./components/VehicleRegistration";
import FuelOwnerRgistration from "./pages/FuelOwnerRgistration";
import FuelStationRegistration from "./pages/FuelStationRegistration"
import {BrowserRouter, Routes, Route} from 'react-router-dom'

import { OwnerLogin, StationLogin } from "./components/LoginComponent";
import OwnerHomePage from "./pages/OwnerHomePage";
import StationHomePage from "./pages/StationHomePage";

import HomePage from "./pages/HomePage";

import AboutUsPage from "./pages/AboutUsPage";

import VehicleManagement from "./components/VehicleManagement";
import AdminDashboard from "./pages/AdminDashboard";
import VehicleOwnerDashboard from "./pages/VehicleOwnerDashboard";

import FuelStationManagement from "./components/FuelStationManagement";



function App() {

  return (
    <>
      <BrowserRouter>
      <Routes> 

        <Route path="/vehicle-registration" element={<VehicleRegistration />}></Route>
        <Route path="/" element={<HomePage/>}></Route>
        <Route path="/about" element={<AboutUsPage/>}></Route>
        <Route path="/vehicleManagement" element={<VehicleManagement/>}></Route>
        <Route path="/adminDashboard" element={<AdminDashboard/>}></Route>
        <Route path="/vehicleOwnerDashboard" element={<VehicleOwnerDashboard/>}></Route>


        <Route path="/fuelStationManagement" element={<FuelStationManagement/>}></Route>


        <Route path="/ownerlogin" element={<OwnerLogin/>}></Route>

        <Route path="/ownerreg" element={<FuelOwnerRgistration/>}></Route>
        <Route path='owner/:id' element={<OwnerHomePage/>}></Route>
        <Route path="/owner/:id/stationreg" element={<FuelStationRegistration/>}></Route>

        <Route path="/stationlogin" element={<StationLogin/>}></Route>
        
        <Route path='station/:id' element={<StationHomePage/>}></Route>
        <Route path="station/reg" ></Route>
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
