import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import StationNavbar from '../components/StationNavbar';
import Footer from '../components/Footer';
import { getfuelInventory, getstationname } from '../Services/FuelStationService';

const StationHomePage = () => {
  const { id } = useParams();
  const [fuels, setFuels] = useState([]); // Initialize with an empty array
  const [name, setName] = useState('');

  useEffect(() => {
    // Fetch fuel inventory
    getfuelInventory(id)
      .then((response) => {
        const fuelData = Object.entries(response.data).map(([fuelType, quantity]) => ({
          fuelType,
          quantity,
        }));
        setFuels(fuelData); // Set the fuel data as an array of objects
      })
      .catch((error) => {
        console.error('Error fetching fuel inventory:', error);
      });

    // Fetch station name
    getstationname(id)
      .then((response) => {
        setName(response.data);
      })
      .catch((error) => {
        console.error('Error fetching station name:', error);
      });
  }, [id]);

  useEffect(() => {
    fuels.forEach((fuel) => {
      if (fuel.quantity <= 100) {
        alert(`Fuel Type: ${fuel.fuelType} is low. Consider refilling.`);
      }
    });
  }, [fuels]);

  return (
    <>
      <StationNavbar />
      <header className="text-black text-left py-1 ps-4 ms-4">
        <h1 className="fw-bold">{name.toLocaleUpperCase()}</h1>
      </header>
      <main className="container my-2">
        <h2 className="fw-bold mb-3">Balance Fuels</h2>
        <div className="row g-4" style={{ margin: '10px' }}>
          {fuels.length > 0 ? (
            fuels.map((fuel, index) => {
              const bgColor = fuel.quantity > 100 ? 'green' : 'red';

              return (
                <div
                  key={index}
                  className="card shadow-sm d-flex flex-column align-items-center justify-content-center"
                  style={{
                    width: '200px',
                    height: '100px',
                    marginRight: '10px',
                    padding: '0',
                  }}
                >
                  <div
                    className="bg-primary text-white fw-bold px-1 py- text-center"
                    style={{ width: '100%', height: '150px' }}
                  >
                    {fuel.fuelType}
                  </div>
                  <div
                    className="card-body text-center"
                    style={{
                      width: '100%',
                      backgroundColor: bgColor,
                      margin: '0',
                    }}
                  >
                    <h5 className="card-title">{fuel.quantity} Liters</h5>
                  </div>
                </div>
              );
            })
          ) : (
            <p>Loading fuels...</p>
          )}
        </div>
      </main>
      <Footer />
    </>
  );
};

export default StationHomePage;
