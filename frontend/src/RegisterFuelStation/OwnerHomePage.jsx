import React, { useState, useEffect } from 'react';
import './OwnerHomePage.css';
import { useParams } from 'react-router-dom';

const OwnerHomePage = () => {
  const {id} = useParams(); 


 

  useEffect(() => {
    // Fetch owner and fuel station data from an API or global state
    // Example:
    // axios.get('/api/owner/details').then(response => {
    //   setOwnerDetails(response.data);
    // });

    // Example for fuel station data:
    // axios.get('/api/fuel-station/details').then(response => {
    //   setFuelStationDetails(response.data);
    // });
  }, []);

  return (

    <>
    </>
  );
};

export default OwnerHomePage;
