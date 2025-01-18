import React, { useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import OwnerNavbar from "../components/OwnerNavbar";
import Footer from "../components/Footer";
import "../css/Layout.css";

const OwnerHomePage = () => {
  const { id } = useParams();
  const decodedId = decodeURIComponent(id);

  useEffect(() => {
    // Fetch data
  }, []);

  return (
    <>
      <OwnerNavbar />
      <header className="text-black text-left py-1 ps-4 ms-4">
        <h1 className="fw-bold">Hi, {decodedId}! 👋</h1>
      </header>

      <main className="container my-2 ">
        <h2 className="fw-bold mb-3">Your Stations</h2>
        <div className="row g-4">

        </div>
      </main>
      <Footer />
    </>
  );
};

export default OwnerHomePage;
