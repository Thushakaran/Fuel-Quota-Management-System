import React, { useState } from 'react';
import axios from 'axios';

const VehicleRegistration = () => {
  const [formData, setFormData] = useState({
    vehicleNumber: '',
    ownerName: '',
    vehicleType: '',
    fuelType: '',
    fuelQuota: '',
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8082/api/vehicles/register', formData);
      alert('Vehicle registered successfully!');
      console.log('Response:', response.data);
    } catch (error) {
      alert('Failed to register vehicle');
      console.error('Error:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="Vehicle Number"
        value={formData.vehicleNumber}
        onChange={(e) => setFormData({ ...formData, vehicleNumber: e.target.value })}
      />
      <input
        type="text"
        placeholder="Owner Name"
        value={formData.ownerName}
        onChange={(e) => setFormData({ ...formData, ownerName: e.target.value })}
      />
      <input
        type="text"
        placeholder="Vehicle Type"
        value={formData.vehicleType}
        onChange={(e) => setFormData({ ...formData, vehicleType: e.target.value })}
      />
      <input
        type="text"
        placeholder="Fuel Type"
        value={formData.fuelType}
        onChange={(e) => setFormData({ ...formData, fuelType: e.target.value })}
      />
      <input
        type="number"
        placeholder="Fuel Quota"
        value={formData.fuelQuota}
        onChange={(e) => setFormData({ ...formData, fuelQuota: e.target.value })}
      />
      <button type="submit">Register Vehicle</button>
    </form>
  );
};

export default VehicleRegistration;
