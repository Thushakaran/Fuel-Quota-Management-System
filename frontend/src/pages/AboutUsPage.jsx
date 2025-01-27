import React from "react";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import "../css/animate.css";
import "../css/AboutUs.css"

const AboutUsPage = () => {
  return (
    <div>
      <Navbar />
      <header className="hero bg-primary text-white text-center py-5 ">
        <div className="text-cent">
          <h1 className="display-4 mt-5 pt-5 ">About Us</h1>
          <p className="lead">
            Empowering fuel management for a better tomorrow.
          </p>
        </div>
      </header>
      <main className="features container my-5">
        <section>
          <h2 className="text-center mb-4">Our Mission</h2>
          <p className="text-center">
            To provide a comprehensive solution for equitable fuel distribution
            and efficient management during crises.
          </p>
        </section>
        <section className="features text-center my-5">
          <h2 className="mb-4">Our Approach</h2>
          <div className="row g-4">
            <div className="col-md-4">
              <div className="feature-card p-4 border rounded shadow-sm">
                <i className="bi bi-qr-code text-primary display-4 mb-3"></i>
                <h3 >QR Code Integration</h3>
                <p>
                  Utilize unique QR codes for secure and hassle-free fuel
                  tracking.
                </p>
              </div>
            </div>

            <div className="col-md-4">
              <div className="feature-card p-4 border rounded shadow-sm">
                <i className="bi bi-phone text-success display-4 mb-3"></i>
                <h3>Mobile Solutions</h3>
                <p>
                  Scan QR codes and update quotas using our dedicated mobile
                  app.
                </p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="feature-card p-4 border rounded shadow-sm">
                <i className="bi bi-people-fill text-danger display-4 mb-3"></i>
                <h3>Collaboration</h3>
                <p>
                  A collaborative system connecting vehicle owners, fuel
                  stations, and administrators.
                </p>
              </div>
            </div>
          </div>
        </section>
        <section className="my-5">
          <h2 className="text-center mb-4">Our Team</h2>
          <p className="text-center">
            A group of innovative developers dedicated to building reliable and
            impactful solutions for real-world problems.
          </p>
        </section>
      </main>
      <Footer />
    </div>
  );
};

export default AboutUsPage;
