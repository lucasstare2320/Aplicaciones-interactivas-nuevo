import React, { useState } from "react";
import { useDispatch } from "react-redux";
import "bootstrap/dist/css/bootstrap.min.css";
import "./Login.css";
import { useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";
function Login() {
  const dispatch = useDispatch(); // esto es para redux
   const navigate = useNavigate()
  const location = useLocation() // permite saber en que ruta estamos 

  // ESTADOS LOCALES
  const [registerData, setRegisterData] = useState({
    nombre: "",
    apellido: "",
    usuario: "",
    correo: "",
    contraseña: "",
    confirmar: "",
  });

  const [loginData, setLoginData] = useState({
    usuario: "",
    contraseña: "",
  });

  // HANDLERS
  // si tiene (e) significa que esta guardando el contenido de aquello que lo llama
  // e se usa por la palabra "event"
  const handleRegisterChange = (e) => {
    setRegisterData({ ...registerData, [e.target.name]: e.target.value });
  };

  const handleLoginChange = (e) => {
    setLoginData({ ...loginData, [e.target.name]: e.target.value });
  };

  // a futuro esto va a tener una verificacion con el back
  const handleLoginSubmit = (e) => {
    e.preventDefault();
    dispatch({ type: "LOGIN_SUCCESS", payload: { id: 1, nombre: loginData.usuario } });
    alert("Login simulado enviado al reducer");
    navigate("/landingpage")
  };

  // aca va a ir el endpoint para POSTear un usuario
  const handleRegisterSubmit = (e) => {
    e.preventDefault();
    console.log("Datos de registro:", registerData);
    alert("Registro simulado (solo front)");
  };

  return (
    <div className="perfume-bg d-flex align-items-center rounded justify-content-center min-vh-100">
        
        {/* Panel Derecho - Login */}
        <div className="col-md-6 p-5 bg-dark">
          <h3 className="text-gold fw-bold text-center mb-4">INICIA SESIÓN</h3>
          <form onSubmit={handleLoginSubmit}>
            <label className="form-label text-light">Usuario</label>
            <input
              type="text"
              name="usuario"
              className="form-control input-dark mb-3"
              placeholder="Usuario"
              onChange={handleLoginChange}
            />
            <label className="form-label text-light">Contraseña</label>
            <input
              type="password"
              name="contraseña"
              className="form-control input-dark mb-4"
              placeholder="Contraseña"
              onChange={handleLoginChange}
            />
            <button type="submit" className="btn btn-gold w-100 fw-bold py-2">
              INICIAR SESIÓN
            </button>
          </form>
          <p className="text-center text-muted small mt-4 mb-0">
            © 2025 EL CÓDIGO PERFUMERIE. Todos los derechos reservados.
          </p>
          <span onClick={() => navigate("/registro")}>
            No tienes cuenta ? click para registrarte 

          </span>
        </div>
      </div>
  );
}

export default Login;
