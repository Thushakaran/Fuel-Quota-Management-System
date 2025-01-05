import "./App.css";
import VehicleRegistration from "./components/VehicleRegistration";
import FuelOwnerRgistration from "./RegisterFuelStation/FuelOwnerRgistration";
import FuelStationRegistration from "./RegisterFuelStation/FuelStationRegistration"
import {BrowserRouter, Routes, Route} from 'react-router-dom'

import OwnerLogin from "./RegisterFuelStation/OwnerLogin";
import StationLogin from "./RegisterFuelStation/StationLogin";
import OwnerHomePage from "./RegisterFuelStation/OwnerHomePage";

import HomePage from "./pages/HomePage";

import AboutUsPage from "./pages/AboutUsPage";
import FuelStationManagement from "./components/FuelStationManagement";


function App() {

  return (
    <>
      <BrowserRouter>
      <Routes> 

        <Route path="/ownerlogin" element={<OwnerLogin/>}></Route>
        <Route path="/stationlogin" element={<StationLogin/>}></Route>
        <Route path="/stationreg" element={<FuelStationRegistration/>}></Route>
        <Route path="/ownerreg" element={<FuelOwnerRgistration/>}></Route>
        <Route path='owner/:id' element={<OwnerHomePage/>}></Route>

        <Route path="/vehicle-registration" element={<VehicleRegistration />}></Route>
        <Route path="/" element={<HomePage/>}></Route>
        <Route path="/about" element={<AboutUsPage/>}></Route>
        <Route path="/fuelStationManagement" element={<FuelStationManagement/>}></Route>

      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
