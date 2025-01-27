
import React from "react";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import "../css/animate.css"; // Import Animate.css
import "../css/homepage.css"

const HomePage = () => {
  return (
    <div className="home-body">
      <Navbar />
      
      {/* Hero Section with Fade Animation */}
      <header className="hero bg-primary text-white text-center py-5 animate__animated animate__fadeIn">
        <div className="banner ">

          <h1 className="display-4">Welcome to the Fuel Quota Management System</h1>
          <p className="lead">
            Your one-stop solution for managing fuel quotas during a crisis.
          </p>
          <a 
            href="/vehicle-registration" 
            className="btn btn-light btn-lg mt-3 animate__animated animate__pulse animate__infinite"
          >
            Get Started
          </a>
        </div>
      </header>

      {/* Main Content */}
      <main className="container my-5">
        
        {/* Features Section with Scroll Animation */}
        <section className="features text-center">

        <h2 className="mb-4 p-5">How We Help You</h2>
          <div className="out-divider row g-4">
            {/* Feature 1: Vehicle Registration */}
            <div className="divider col-md-4">
              <div className="feature-card p-4 border rounded shadow-sm animate__animated animate__fadeInUp">
         
                <i className="bi bi-car-front-fill text-primary display-4 mb-3"></i>
                <h3>Vehicle Registration</h3>
                <p>Quickly register your vehicles online and get a unique QR code.</p>
              </div>
            </div>
            {/* Feature 2: Real-Time Quota Tracking */}
            <div className="divider col-md-4">
              <div className="feature-card p-4 border rounded shadow-sm animate__animated animate__fadeInUp animate__delay-1s">

                <i className="bi bi-speedometer text-success display-4 mb-3"></i>
                <h3>Real-Time Quota Tracking</h3>
                <p>Monitor your fuel usage and remaining balance anytime.</p>
              </div>
            </div>

            {/* Feature 3: Instant Notifications */}
            <div className="divider col-md-4">
              <div className="feature-card p-4 border rounded shadow-sm animate__animated animate__fadeInUp animate__delay-2s">
                <i className="bi bi-envelope-fill text-danger display-4 mb-3"></i>
                <h3>Instant Notifications</h3>
                <p>Get notified via SMS or email each time you pump fuel.</p>
              </div>
            </div>

          </div>
        </section>
      </main>

      <Footer />
    </div>
  );
};

export default HomePage;
