import "./App.css";
import VehicleRegistration from "./components/VehicleRegistration";
import FuelOwnerRgistration from "./RegisterFuelStation/FuelOwnerRgistration";
import FuelStationRegistration from "./RegisterFuelStation/FuelStationRegistration"
import {BrowserRouter, Routes, Route} from 'react-router-dom'
import OwnerLogin from "./RegisterFuelStation/OwnerLogin";
import StationLogin from "./RegisterFuelStation/StationLogin";

function App() {

  return (
    <>
      <BrowserRouter>
      <Routes> 
        <Route path="" element={<VehicleRegistration />}></Route>
        <Route path="/ownerlogin" element={<OwnerLogin/>}></Route>
        <Route path="/stationlogin" element={<StationLogin/>}></Route>
        <Route path="/stationreg" element={<FuelStationRegistration/>}></Route>
        <Route path="/ownerreg" element={<FuelOwnerRgistration/>}></Route>
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
