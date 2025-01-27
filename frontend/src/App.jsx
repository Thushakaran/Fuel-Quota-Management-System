import VehicleRegistration from "./pages/VehicleRegistration";
import FuelStationRegistration from "./pages/FuelStationRegistration";
import FuelOwnerRegistration from "./pages/FuelOwnerRgistration";
import { BrowserRouter, Routes, Route } from "react-router-dom";


import { AdminLogin, OwnerLogin, StationLogin, VehicleLogin } from "./pages/LoginPage";


import OwnerHomePage from "./pages/OwnerHomePage";
import StationHomePage from "./pages/StationHomePage";

import HomePage from "./pages/HomePage";
import AboutUsPage from "./pages/AboutUsPage";

import VehicleManagement from "./pages/VehicleManagement";
import AdminDashboard from "./pages/AdminDashboard";
import VehicleOwnerDashboard from "./pages/VehicleOwnerDashboard";


import FuelStationManagement from "./pages/FuelStationManagement";
// import OwnerProfile from "./components/OwnerProfile";

import OwnerProfile from "./components/OwnerProfile";

import AddFuelForm from "./components/AddFuelForm";
import FuelTransactionManagement from "./pages/FuelTransactionManagement";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route
            path="/vehicle-registration"
            element={<VehicleRegistration />}
          />
          <Route path="/" element={<HomePage />} />
          <Route path="/about" element={<AboutUsPage />} />
          <Route path="/vehicleManagement" element={<VehicleManagement />} />
          <Route path="/transactionManagement" element={<FuelTransactionManagement />} />
          <Route path="/adminlogin" element={<AdminLogin/>}/>
          <Route path="/adminDashboard" element={<AdminDashboard />} />
          <Route
            path="/fuelStationManagement"
            element={<FuelStationManagement />}
          />

          <Route path="/vehiclelogin" element={<VehicleLogin />} />
          <Route path="/vehicle/:id" element={<VehicleOwnerDashboard />} />

          <Route path="/ownerlogin" element={<OwnerLogin />} />
          <Route path="/ownerreg" element={<FuelOwnerRegistration />} />
          <Route path="owner/:id" element={<OwnerHomePage />} />


          <Route path="/owner/:id/stationreg" element={<FuelStationRegistration />} />
          <Route path="owner/:id/profile" element={<OwnerProfile/>} />
          


          <Route path="/stationlogin" element={<StationLogin />} />
          <Route path="station/:id" element={<StationHomePage />} />
          <Route path="station/:id/addfuel" element={<AddFuelForm />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
