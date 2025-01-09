import React from 'react'
import { useParams } from 'react-router-dom';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';



const StationHomePage = () => {
    const {id} = useParams(); 
  return (
    <>
      {/* temporay Navbar */}
      <Navbar />    
      <div>StationHomePage {id}</div>
      {/* temporayFooter  */}
      <Footer/>
    </>
    )
}

export default StationHomePage