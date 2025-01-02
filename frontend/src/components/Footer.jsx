// src/components/Footer.js
import React from "react";

const Footer = () => {
  return (
    <footer className="bg-dark text-white text-center py-3">
      <p>&copy; 2024 Fuel Quota Management System. All rights reserved.</p>
      <p>
        <a
          href="https://www.facebook.com"
          target="_blank"
          rel="noopener noreferrer"
          className="text-white me-3"
        >
          <i className="bi bi-facebook" style={{ color: "#4267B2" }}></i>
        </a>
        <a
          href="https://www.twitter.com"
          target="_blank"
          rel="noopener noreferrer"
          className="text-white me-3"
        >
          <i className="bi bi-twitter" style={{ color: "#1DA1F2" }}></i>
        </a>
        <a
          href="https://www.instagram.com"
          target="_blank"
          rel="noopener noreferrer"
          className="text-white"
        >
          <i className="bi bi-instagram" style={{ color: "#C13584" }}></i>
        </a>
      </p>
    </footer>
  );
};

export default Footer;
