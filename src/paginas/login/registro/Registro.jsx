import React, { useState } from "react";
import { useDispatch } from "react-redux";
import "bootstrap/dist/css/bootstrap.min.css";
import "./Login.css";
import { useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";
function Registro() {
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
        {/* Panel Izquierdo - Registro */}
        <div className="col-md-6 p-5 border-end border-dark-subtle">
          <h3 className="text-center text-gold fw-bold mb-1">EL CÓDIGO PERFUMERIE</h3>
          <p className="text-center text-muted mb-4" onClick={() => navigate("/")}>
            ¿Ya tienes una cuenta?{" "}
            <span className="text-gold fw-semibold" style={{ cursor: "pointer" }}>
              Inicia sesión aquí
            </span>
          </p>
          <form onSubmit={handleRegisterSubmit}>
            <div className="row mb-3">
              <div className="col">
                <label className="form-label text-light">Nombre</label>
                <input
                  type="text"
                  name="nombre"
                  className="form-control input-dark"
                  placeholder="Nombre"
                  onChange={handleRegisterChange}
                />
              </div>
              <div className="col">
                <label className="form-label text-light">Apellido</label>
                <input
                  type="text"
                  name="apellido"
                  className="form-control input-dark"
                  placeholder="Apellido"
                  onChange={handleRegisterChange}
                />
              </div>
            </div>

            <div className="mb-3">
              <label className="form-label text-light">Nombre de Usuario</label>
              <input
                type="text"
                name="usuario"
                className="form-control input-dark"
                placeholder="usuario123"
                onChange={handleRegisterChange}
              />
            </div>

            <div className="mb-3">
              <label className="form-label text-light">Correo electrónico</label>
              <input
                type="email"
                name="correo"
                className="form-control input-dark"
                placeholder="tucorreo@email.com"
                onChange={handleRegisterChange}
              />
            </div>

            <div className="mb-3">
              <label className="form-label text-light">Contraseña</label>
              <input
                type="password"
                name="contraseña"
                className="form-control input-dark"
                placeholder="********"
                onChange={handleRegisterChange}
              />
            </div>

            <div className="mb-4">
              <label className="form-label text-light">Confirmar contraseña</label>
              <input
                type="password"
                name="confirmar"
                className="form-control input-dark"
                placeholder="********"
                onChange={handleRegisterChange}
              />
            </div>

            <button type="submit" className="btn btn-gold w-100 fw-bold py-2">
              REGISTRARSE
            </button>
          </form>

          <p className="text-center mt-4 small text-secondary">
            Al registrarte, aceptas nuestros{" "}
            <span className="text-gold">Términos de Servicio</span> y{" "}
            <span className="text-gold">Política de Privacidad</span>.
          </p>
        </div>

      </div>
  );
}

export default Registro;
