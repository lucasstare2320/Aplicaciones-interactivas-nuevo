import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import ProductCard from "../landingpage/ProductCard";
import "bootstrap/dist/css/bootstrap.min.css";
import Navbarperfume from "../landingpage/NAVBAR/Navbar";
import Footer from "../landingpage/FOOTER/Footer";

const Productos = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const admin = useSelector((state) => state.user?.admin); // obtener admin del reducer

    // Props definidos, esto lo vamos a sacar cuando hagamos un fetch de la base de datos para obtener productos
    const productosData = [
        {
            id: "chanel-n5",
            name: "Chanel N°5",
            brand: "Chanel",
            price: 135,
            currency: "€",
            descripcion: "El clásico eterno de Chanel.",
            gender: "Femenino",
            type: "Eau de Parfum",
            image:
                "https://images.unsplash.com/photo-1616627989394-7e3f0e8c5c2e?auto=format&fit=crop&w=800&q=60",
        },
        {
            id: "dior-jadore",
            name: "J’adore",
            brand: "Dior",
            price: 120,
            currency: "€",
            descripcion: "Fragancia floral luminosa.",
            gender: "Femenino",
            type: "Eau de Parfum",
            image:
                "https://images.unsplash.com/photo-1607083206173-0c2f5b8f9e3d?auto=format&fit=crop&w=800&q=60",
        },
        {
            id: "lancome-vie",
            name: "La Vie Est Belle",
            brand: "Lancôme",
            price: 150,
            currency: "€",
            descripcion: "La vida es bella con Lancôme.",
            gender: "Femenino",
            type: "Eau de Parfum",
            image:
                "https://images.unsplash.com/photo-1586871011698-6d78f8051cbb?auto=format&fit=crop&w=800&q=60",
        },
        {
            id: "ysl-opium",
            name: "Black Opium",
            brand: "Yves Saint Laurent",
            price: 130,
            currency: "€",
            descripcion: "Intensa y adictiva.",
            gender: "Femenino",
            type: "Eau de Parfum",
            image:
                "https://images.unsplash.com/photo-1591471756638-6eaef76bd66d?auto=format&fit=crop&w=800&q=60",
        },
        {
            id: "dg-lightblue",
            name: "Light Blue",
            brand: "Dolce & Gabbana",
            price: 110,
            currency: "€",
            descripcion: "Fresca y mediterránea.",
            gender: "Femenino",
            type: "Eau de Toilette",
            image:
                "https://images.unsplash.com/photo-1574607380899-525b8d0aee22?auto=format&fit=crop&w=800&q=60",
        },
        {
            id: "ch-goodgirl",
            name: "Good Girl",
            brand: "Carolina Herrera",
            price: 125,
            currency: "€",
            descripcion: "Atrevida y sofisticada.",
            gender: "Femenino",
            type: "Eau de Parfum",
            image:
                "https://images.unsplash.com/photo-1581067725929-ce1960cdd42d?auto=format&fit=crop&w=800&q=60",
        },
    ];


    // variables de filtrado
    const [selectedGender, setSelectedGender] = useState("Todos");
    const [selectedBrand, setSelectedBrand] = useState("Todas");
    const [priceRange, setPriceRange] = useState([0, 200]);
    const [sortOrder, setSortOrder] = useState("asc");

    const [productos, setProductos] = useState(productosData); // estado con productos estos son los datos que van al reducer y luego al detalle

    // variables del producto nuevo
    const [newProduct, setNewProduct] = useState({
        id: "",
        name: "",
        brand: "",
        price: 0,
        currency: "€",
        descripcion: "",
        gender: "",
        type: "",
        image: "",
    });

    // filtrado en si
    const filteredProducts = productos
        .filter((p) => (selectedGender === "Todos" ? true : p.gender === selectedGender))
        .filter((p) => (selectedBrand === "Todas" ? true : p.brand === selectedBrand))
        .filter((p) => p.price >= priceRange[0] && p.price <= priceRange[1])
        .sort((a, b) => (sortOrder === "asc" ? a.price - b.price : b.price - a.price));





    // al hacer click, se envia al reducer la informacion del producto seleccionado
    const handleProductClick = (product) => {
        dispatch({ type: "SET_SELECTED_PRODUCT", payload: product });
            navigate(`/detalle/:${product.id}`) // CAMBIAR A FUTURO por el id del producto del fetch

    };


    // AGREGAR AL CARRITO
    const handleAddToCart = (product) => {
        // se agrega la informacion del elemento y se enviia al listado del reducer
        dispatch({
            type: "ADD_TO_CART",
            payload: {
                id: product.id,
                name: product.name,
                brand: product.brand,
                price: product.price,
                currency: product.currency,
                image: product.image,
                qty: 1,
            },
        });
    };

    const handleNewProductChange = (e) => {
        const { name, value } = e.target;
        setNewProduct((prev) => ({ ...prev, [name]: value }));
    };

    const handleAddProduct = () => {
        setProductos((prev) => [...prev, { ...newProduct }]);
        setNewProduct({
            id: "",
            name: "",
            brand: "",
            price: 0,
            currency: "€",
            descripcion: "",
            gender: "",
            type: "",
            image: "",
        });
    };

    return (
        <>
            <Navbarperfume />
            <div className="container-fluid py-4" style={{ backgroundColor: "#000" }}>
                <div className="row">
                    {/* Filtros */}
                    <aside className="col-md-3 text-white mb-4">
                        <h4 className="mb-3" style={{ color: "#d4af37" }}>Filtros</h4>
                        {/* Género */}
                        <div className="mb-3">
                            <label className="form-label">Género</label>
                            <select
                                className="form-select"
                                value={selectedGender}
                                onChange={(e) => setSelectedGender(e.target.value)}
                            >
                                <option value="Todos">Todos</option>
                                <option value="Femenino">Femenino</option>
                                <option value="Masculino">Masculino</option>
                            </select>
                        </div>
                        {/* Marca */}
                        <div className="mb-3">
                            <label className="form-label">Marca</label>
                            <select
                                className="form-select"
                                value={selectedBrand}
                                onChange={(e) => setSelectedBrand(e.target.value)}
                            >
                                <option value="Todas">Todas</option>
                                {Array.from(new Set(productos.map((p) => p.brand))).map((brand) => (
                                    <option key={brand} value={brand}>{brand}</option>
                                ))}
                            </select>
                        </div>
                        {/* Rango de precio */}
                        <div className="mb-3">
                            <label className="form-label">Precio máximo: {priceRange[1]}€</label>
                            <input
                                type="range"
                                className="form-range"
                                min="50"
                                max="200"
                                step="5"
                                value={priceRange[1]}
                                onChange={(e) => setPriceRange([priceRange[0], parseInt(e.target.value)])}
                            />
                        </div>

                        <div className="mb-3">
                            <label className="form-label">
                                Precio mínimo: {priceRange[0]}€
                            </label>
                            <input
                                type="range"
                                className="form-range"
                                min="0"
                                max={priceRange[1]} // el máximo del slider no puede superar el máximo actual
                                step="5"
                                value={priceRange[0]}
                                onChange={(e) =>
                                    setPriceRange([parseInt(e.target.value), priceRange[1]])
                                }
                            />
                        </div>



                        {/* Formulario para admin */}
                        {/* 
                        {admin && (
                            <div className="mt-4 p-3 border border-warning rounded">
                                <h5 className="text-warning mb-3">Agregar producto nuevo</h5>
                                {["name", "brand", "price", "descripcion", "gender", "type", "image", "stock", "idcategoria","Descuento"].map((field) => (
                                    <div className="mb-2" key={field}>
                                        <input
                                            className="form-control"
                                            placeholder={field}
                                            name={field}
                                            value={newProduct[field]}
                                            onChange={handleNewProductChange}
                                        />
                                    </div>
                                ))}
                                <button className="btn btn-warning w-100 mt-2" onClick={handleAddProduct}>
                                    Agregar producto
                                </button>
                            </div>
                        )}*/}
                    </aside>

                    {/* Listado */}
                    <main className="col-md-9">
                        <div className="d-flex justify-content-between align-items-center mb-3">
                            <h3 className="text-white">Perfumes</h3>
                            <div>
                                <label className="me-2 text-white">Ordenar por:</label>
                                <select
                                    className="form-select d-inline-block w-auto"
                                    value={sortOrder}
                                    onChange={(e) => setSortOrder(e.target.value)}
                                >
                                    <option value="asc">Precio (ascendente)</option>
                                    <option value="desc">Precio (descendente)</option>
                                </select>
                            </div>
                        </div>

                        <div className="row g-4">
                            {filteredProducts.map((product) => (
                                <div
                                    className="col-12 col-sm-6 mb-4 col-lg-4"
                                    key={product.id}
                                    onClick={() => handleProductClick(product)}
                                    style={{ cursor: "pointer" }}
                                >
                                    
                                    <ProductCard product={product} onAddToCart={handleAddToCart} />


                                </div>
                            ))}
                        </div>
                    </main>
                </div>
            </div>
            <Footer />
        </>
    );
};

export default Productos;
