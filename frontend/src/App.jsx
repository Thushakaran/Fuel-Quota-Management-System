import "./App.css";
import VehicleRegistration from "./components/VehicleRegistration";
import FuelStationRegistration from "./RegisterFuelStation/FuelStationRegistration"
import {BrowserRouter, Routes, Route} from 'react-router-dom'

function App() {

  return (
    <>
      
      <BrowserRouter>
      <Routes> 
        <Route path="" element={<VehicleRegistration />}></Route>
        <Route path="/stationregister" element={<FuelStationRegistration/>}></Route>
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
