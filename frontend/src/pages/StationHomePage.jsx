import React from 'react'
import { useParams } from 'react-router-dom';
import StationNavbar from '../components/StationNavbar';
import Footer from '../components/Footer';



const StationHomePage = () => {
    const {id} = useParams(); 
  return (
    <>
      <StationNavbar />    
      <header className="text-black text-left py-1 ps-4 ms-4">
        <h1 className="fw-bold">{id}</h1>
      </header>
      <main className="container my-2 ">
        <h2 className="fw-bold mb-3">Balance Fuels</h2>
        <div className="row g-4">

        </div>
      </main>
      <Footer/>
    </>
    )
}

export default StationHomePage