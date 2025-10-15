import React from 'react'
import { Route, Routes } from 'react-router-dom'
import Login from './login/Login'
import LandingPage from './landingpage/Landingpage'
import ProductDetail from './productdetail/ProductDetail'
import Productos from './productos/Productos'
import Carrito from './carrito/Carrito'
import Usuario from './perfil/Usuario'
import Checkout from './checkout/Checkout'
import Misproductos from './misproductos/misproductos'
//este componente sirve para hacer las rutas, es decir, cambiar entre paginas sin recargar
// se pueden poner rutas en app pero en gral me gusta en un componente separado


function RUTAS() {

  return (
    <>
    <Routes>
      <Route path='/' element={<Login/>}></Route>
      <Route path='/landingpage' element={<LandingPage></LandingPage>}></Route>
      <Route path='/detalle/:id' element={<ProductDetail/>}></Route>
      <Route path='/productos' element={<Productos/>}></Route>
      <Route path='/carrito' element={<Carrito/>}></Route>
      <Route path='/perfil' element={<Usuario/>}></Route>
      <Route path='/checkout' element={<Checkout/>}></Route>
      <Route path='/misproductos' element={<Misproductos/>}></Route>
    </Routes>     
    </>
  )
}

export default RUTAS