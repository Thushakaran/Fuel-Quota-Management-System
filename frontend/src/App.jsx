import VehicleRegistration from "./components/VehicleRegistration";
import FuelOwnerRgistration from "./pages/FuelOwnerRgistration";
import FuelStationRegistration from "./RegisterFuelStation/FuelStationRegistration"
import {BrowserRouter, Routes, Route} from 'react-router-dom'

import OwnerLogin from "./pages/OwnerLogin";
import StationLogin from "./pages/StationLogin";
import OwnerHomePage from "./pages/OwnerHomePage";

import HomePage from "./pages/HomePage";

import AboutUsPage from "./pages/AboutUsPage";
import VehicleManagement from "./components/VehicleManagement";
import AdminDashboard from "./pages/AdminDashboard";
import VehicleOwnerDashboard from "./pages/VehicleOwnerDashboard";


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
        <Route path="/vehicleManagement" element={<VehicleManagement/>}></Route>
        <Route path="/adminDashboard" element={<AdminDashboard/>}></Route>
        <Route path="/vehicleOwnerDashboard" element={<VehicleOwnerDashboard/>}></Route>

      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
