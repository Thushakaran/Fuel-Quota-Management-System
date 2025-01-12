import React from "react";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";

const HomePage = () => {
  return (
    <div>
      <Navbar />
      <header className="hero bg-primary text-white text-center py-5">
        <div>
          <h1 className="display-4">Welcome to the Fuel Quota Management System</h1>
          <p className="lead">
            Your one-stop solution for managing fuel quotas during a crisis.
          </p>
          <a href="/vehicle-registration" className="btn btn-light btn-lg mt-3">
            Get Started
          </a>
        </div>
      </header>
      <main className="container my-5">
        <section className="features text-center">
          <h2 className="mb-4">How We Help You</h2>
          <div className="row g-4">
            <div className="col-md-4">
              <div className="feature-card p-4 border rounded shadow-sm">
                <i className="bi bi-car-front-fill text-primary display-4 mb-3"></i>
                <h3>Vehicle Registration</h3>
                <p>Quickly register your vehicles online and get a unique QR code.</p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="feature-card p-4 border rounded shadow-sm">
                <i className="bi bi-speedometer text-success display-4 mb-3"></i>
                <h3>Real-Time Quota Tracking</h3>
                <p>Monitor your fuel usage and remaining balance anytime.</p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="feature-card p-4 border rounded shadow-sm">
                <i className="bi bi-envelope-fill text-danger display-4 mb-3"></i>
                <h3>Instant Notifications</h3>
                <p>
                  Get notified via SMS or email each time you pump fuel.
                </p>
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
