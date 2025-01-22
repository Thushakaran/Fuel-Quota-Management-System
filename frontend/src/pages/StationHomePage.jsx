import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import StationNavbar from '../components/StationNavbar';
import Footer from '../components/Footer';
import { getfuelInventory, getstationname } from '../api/FuelStationServiceApi.js';

const StationHomePage = () => {
  const { id } = useParams();
  const [fuels, setFuels] = useState([]);     //initialize with an empty array

  const [name, setName] = useState('');

  const fetchData = () => {
    // Fetch fuel inventory
    getfuelInventory(id)
      .then((response) => {
        const fuelData = Object.entries(response.data).map(([fuelType, quantity]) => ({
          fuelType,
          quantity,
        }));
        setFuels(fuelData); //set fuel data as array of objects
      })
      .catch((error) => {
        console.error('Error fetching fuel inventory:', error);
      });

    //fetch station name
    getstationname(id)
      .then((response) => {
        setName(response.data);
      })
      .catch((error) => {
        console.error('Error fetching station name:', error);
      });
  };

  useEffect(() => {
    fetchData(); 
    const intervalId = setInterval(fetchData, 5000); 
    //refresh the page every 5 seconds
    return () => clearInterval(intervalId);
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
      <main className="container my-2" style={{ width: '800px' }}>
        <h2 className="fw-bold mb-2">Balance Fuels</h2>
        <div className="row g-3" style={{ margin: '10px' }}>
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
        <br />
      </main>
      <div style={{ position: 'absolute', bottom: '0', display: 'block', width: '100%' }}>
        <Footer />
      </div>
    </>
  );
};

export default StationHomePage;
