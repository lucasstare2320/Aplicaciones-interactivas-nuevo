import React from "react";
import { useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js"; // necesario para el toggle
import { FaShoppingCart, FaUser } from "react-icons/fa"; // íconos blancos
import "./Navbarperfume.css";
import { useSelector } from "react-redux";

function Navbarperfume() {
  const navigate = useNavigate();
     const admin = useSelector((state) => state.user?.admin); // obtener admin del reducer

  return (
    <nav className="navbar navbar-expand-lg navbar-dark sticky-top custom-navbar">
      <div className="container">
        {/* Logo + Nombre */}
        <span
          className="navbar-brand brand-text"
          onClick={() => navigate("/landingpage")}
        >
          <span className="brand-gold">EL CÓDIGO</span>{" "}
          <span className="brand-white">PERFUMERIE</span>
        </span>

        {/* Botón toggle responsive */}
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarPerfume"
          aria-controls="navbarPerfume"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        {/* Links */}
        <div className="collapse navbar-collapse" id="navbarPerfume">
          <ul className="navbar-nav ms-auto mb-2 mb-lg-0 align-items-lg-center">
            <li className="nav-item">
                {admin && (
                <span onClick={() => navigate("/misproductos")}>
                  mis productos
                </span>

              )}
            </li>

            <li className="nav-item">

              
            
              <span
                className="nav-link"
                onClick={() => navigate("/productos")}
              >
                Productos
              </span>
            </li>
            <li className="nav-item">
              <span
                className="nav-link"
                onClick={() => navigate("/")}
              >
                Logout
              </span>
            </li>
            <li className="nav-item">
              <span
                className="nav-link icon-link"
                onClick={() => navigate("/carrito")}
              >
                <FaShoppingCart />
              </span>
            </li>
            <li className="nav-item">
              <span
                className="nav-link icon-link"
                onClick={() => navigate("/perfil")}
              >
                <FaUser />
              </span>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}

export default Navbarperfume;