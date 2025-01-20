import React from "react";
import { Link , useNavigate} from "react-router-dom";
import { useParams } from "react-router-dom";

const StationNavbar = () => {
  const { id } = useParams();
  const navigate = useNavigate(); // Hook for navigation in React Router v6

  // Logout function to clear token and navigate to home page
  const handleLogout = () => {
    localStorage.removeItem('token'); // Remove token from localStorage
    navigate('/'); // Navigate to the home page
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
      <div className="container-fluid">
        <a
          className="navbar-brand d-flex align-items-center ps-3 ms-3"
          href="/"
          style={{ fontSize: "1.5rem" }}
        >
          <i
            className="bi bi-fuel-pump-fill me-2"
            style={{ color: "#ffdd57" }}
          ></i>
          Fuel Quota System
        </a>

        <div className="d-flex align-items-center ms-auto">
          <div className="dropdown">
            <button
              className="btn btn-link p-0"
              type="button"
              id="profileDropdown"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <div
                className="rounded-circle bg-white d-flex justify-content-center align-items-center"
                style={{ width: "65px", height: "65px" }}
              >
               <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAACXBIWXMAAAsTAAALEwEAmpwYAAADb0lEQVR4nO2au2sUURTGf18CIvgoRHwVNoqvIoJGMGIaJURtFGGxsNNe/wJBsLExWopoY1R0C5+Fj4SANj4aG5OIsQiIK75AtJPIyIW7eLnOTHZnNzs7m/vBgd3ZM+fM+e7rnDMLAQEBAQEBAQHZ0ANcBqaB30DUoHwGHgJHAKX4PQB8A64DC8gBAk4Df5oQdJKMAcsT/F909O7lQcJp72HfAXeBcoPyAphx7L4CFsb4Xw9U8iKhxxn5X8DxJtvfALx2gjuVoLcxLxKuOE6bHXwVa4Ef1sdXoLtGEm7QAkxbZ1Nz7OesE9j2FD1DwierZ5bPnOO3dWbWfBVmhPYDpQbkMLDCsXnUIeDgLM9UdnTnHJEV47SKM03a+ScdmyXnuvnc1gSUm3j8FZ6AUgZ53kkEZEHc/YEAwgwgLAEKuAeU5vsmGDUgP4tKwJkGA5+ymWSXY7PL1v3vi0BAN7Cvzmlv0ttB+9lNgX2sshVoWxOQNzqGgG7gkJWkErijCdgpKTIC7O5kAlZKGpb0AFhsr22WNFYlQNIzc83+ttjoSrqasF8UjoDjTqDfJVWc775UrE51ZhyLsbcGuFMkAvpSAk4VYGuCTXNsjlIAAroklbMSIOmWly+4WEebErDSNlB3pQT/RtI54IQRSUOSxlNI6LM203KI9iBA0rWUEX0D7Em5fUDSRMr9wxSAgPsJD//IOQXSsETSkwQb92kxogxLYIGkLzEjX0vwLgn+TDDvDBbRYkRZNkFJn7zdfG8G3wP+EUkOiDIQsCVm9DPBnwVOstSWBHSZlNZkdR4BZrePww5Jr4wAvXEKks57tp4C/XXWDi0j4FBCMmOOuv9gg6/qvUyweTLBpimgWoIoEEDNM8BMy/6YJTCUoN9rRl6S+Z/AthqXwIipItt1CeBUeu5Dj5MRkia9qb+JghyDFe/BBzL4HvSI/EgOiJqUCE2Y5KYOG0slvfVsfClEIqTkVPhxjSSY4EcKmworvRiamGU5DMaMfLGKIf6Vw30p5bDJ8M6bc96e9Rf8Dc8rh3tsd6j9y+EmN0RupjREVsznlpjBMgrYFP3mV4p+xWd10pqiLUXU5Lb4IidZcjPG0Tra4rkQ8KHOv8GursF2vzPSJr1tS8xkfAN8O2XzavTVWEtxqQESRlvVug4ICAgICAgIYBb8BdvX7F7lCu01AAAAAElFTkSuQmCC" alt="external-fuel-mutuline-maps-and-location-others-zufarizal-robiyanto" zoom="0.8"/>
              </div>
            </button>
            <ul
              className="dropdown-menu dropdown-menu-end"
              aria-labelledby="profileDropdown"
            >
              <li>
                <Link to="/profile" className="dropdown-item">
                  Profile
                </Link>
              </li>
              <li>
                <hr className="dropdown-divider" />
              </li>
              <li>
                <button onClick={handleLogout} className="dropdown-item">
                  Logout
                </button>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default StationNavbar;