import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import ProductCard from "../landingpage/ProductCard"; // lo dejamos (no usado) por si lo querés recuperar
import "bootstrap/dist/css/bootstrap.min.css";
import Navbarperfume from "../landingpage/NAVBAR/Navbar";
import Footer from "../landingpage/FOOTER/Footer";
import { useState } from "react";

const Misproductos = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const admin = useSelector((state) => state.user?.admin); // obtener admin del reducer

    const [sortOrder, setSortOrder] = React.useState("asc");

    // variables del producto nuevo (incluir todas las propiedades para inputs controlados)
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
        stock: 0,
        idcategoria: "",
        Descuento: 0,
    });

    // este componente es el componente de productos pero con todos los filtros etc quitado
    // a futuro aca iran los productos creados por el admin y se hara con un fetch
    // esta vista solo la ve el admin

    // remplazar esto por fetch !
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
            stock: 10,
            idcategoria: "",
            Descuento: 0,
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
            stock: 8,
            idcategoria: "",
            Descuento: 0,
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
            stock: 5,
            idcategoria: "",
            Descuento: 0,
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
            stock: 7,
            idcategoria: "",
            Descuento: 0,
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
            stock: 12,
            idcategoria: "",
            Descuento: 0,
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
            stock: 6,
            idcategoria: "",
            Descuento: 0,
        },
    ];

    const [productos, setProductos] = useState(productosData); // estado con productos estos son los datos que van al reducer y luego al detalle

    const handleProductClick = (product) => {
        dispatch({ type: "SET_SELECTED_PRODUCT", payload: product });
        navigate(`/detalle/${product.id}`); // ojo: saca los ":" dentro de la ruta
    };

    const handleAddToCart = (product) => {
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

    // usar estado productos para ordenar y mostrar (no productosData fijo)
    const sortedProducts = [...productos].sort((a, b) => {
        if (sortOrder === "asc") return a.price - b.price;
        return b.price - a.price;
    });

    const handleNewProductChange = (e) => {
        const { name, value, type } = e.target;
        // convertir a número si corresponde
        const numericFields = ["price", "stock", "Descuento"];
        let val = value;
        if (numericFields.includes(name)) {
            // permitir campo vacío temporalmente
            val = value === "" ? "" : Number(value);
        }
        setNewProduct((prev) => ({ ...prev, [name]: val }));
    };

    const handleAddProduct = () => {
        // validaciones básicas
        if (!newProduct.name || newProduct.name.trim() === "") {
            alert("El producto necesita un nombre.");
            return;
        }
        if (newProduct.price === "" || Number(newProduct.price) <= 0) {
            alert("El producto necesita un precio mayor a 0.");
            return;
        }

        // asignar un id si no existe
        const idToUse = newProduct.id && newProduct.id.toString().trim() !== "" ? newProduct.id : `p-${Date.now()}`;

        // preparar objeto final con tipos correctos
        const productoFinal = {
            ...newProduct,
            id: idToUse,
            price: Number(newProduct.price),
            stock: Number(newProduct.stock || 0),
            Descuento: Number(newProduct.Descuento || 0),
            currency: newProduct.currency || "€",
            // asegurarse de tener campos que la UI espera
            descripcion: newProduct.descripcion || "",
            gender: newProduct.gender || "",
            type: newProduct.type || "",
            image:
                newProduct.image ||
                "https://via.placeholder.com/400x300?text=No+image",
            idcategoria: newProduct.idcategoria || "",
        };

        // agregar al estado de productos
        setProductos((prev) => [...prev, productoFinal]);

        // limpiar formulario (volver a estado inicial completo)
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
            stock: 0,
            idcategoria: "",
            Descuento: 0,
        });
    };

    const toggleSortOrder = () => setSortOrder((s) => (s === "asc" ? "desc" : "asc"));

    // --- Mini componente interno (tu "mini prop") que NO muestra "Agregar al carrito"
    const MiniProductCard = ({ product }) => {
        return (
            <div className="card h-100 shadow-sm">
                <div style={{ height: 200, overflow: "hidden", display: "flex", alignItems: "center", justifyContent: "center" }}>
                    <img
                        src={product.image}
                        alt={product.name}
                        style={{ maxWidth: "100%", maxHeight: "100%", objectFit: "cover" }}
                        onError={(e) => (e.target.src = "https://via.placeholder.com/400x300?text=No+image")}
                    />
                </div>
                <div className="card-body p-3">
                    <h6 className="card-title mb-1 text-white" style={{ fontSize: 16 }}>{product.name}</h6>
                    <p className="mb-1 text-white" style={{ fontSize: 13 }}>{product.brand}</p>
                    <div className="d-flex text-white justify-content-between align-items-center mt-2">
                        <span className="fw-bold text-white">{product.price}{product.currency || "€"}</span>
                        <small className="text-white">Stock: {product.stock ?? 0}</small>
                    </div>
                </div>
                {admin && (
                    <div className="d-flex justify-content-between p-2 m-1">
                        <button className="btn btn-sm btn-outline-warning" disabled>
                            Editar
                        </button>
                        <button className="btn btn-sm btn-outline-danger" disabled>
                            Eliminar
                        </button>
                    </div>
                )}
            </div>
        );
    };
    // --- fin MiniProductCard

    return (
        <>
            <Navbarperfume />
            <div className="container-fluid py-4" style={{ backgroundColor: "#000" }}>
                <div className="row">
                    <main className="col-md-9 mx-auto">
                        <div className="d-flex justify-content-between align-items-center mb-3">
                            <h3 className="text-white">Mis Perfumes</h3>
                            <div>
                                <button className="btn btn-sm btn-outline-light me-2" onClick={toggleSortOrder}>
                                    Ordenar por precio: {sortOrder === "asc" ? "Asc" : "Desc"}
                                </button>
                            </div>
                        </div>

                        <div className="row g-4">
                            {sortedProducts.map((product) => (
                                <div
                                    className="col-12 col-sm-6 mb-4 col-lg-4"
                                    key={product.id}
                                    onClick={() => handleProductClick(product)}
                                    style={{ cursor: "pointer" }}
                                >
                                    {/* usamos nuestro mini componente, sin texto de 'Agregar al carrito' */}
                                    <MiniProductCard product={product} />


                                </div>
                            ))}
                        </div>

                        {admin && (
                            <div className="mt-4 p-3 border border-warning rounded">
                                <h5 className="text-warning mb-3">Agregar producto nuevo</h5>
                                {[
                                    "id",
                                    "name",
                                    "brand",
                                    "price",
                                    "descripcion",
                                    "gender",
                                    "type",
                                    "image",
                                    "stock",
                                    "idcategoria",
                                    "Descuento",
                                ].map((field) => (
                                    <div className="mb-2" key={field}>
                                        <input
                                            className="form-control"
                                            placeholder={field}
                                            name={field}
                                            // evitar warning controlled/uncontrolled
                                            value={newProduct[field] ?? ""}
                                            onChange={handleNewProductChange}
                                            // usar input number para campos numéricos
                                            type={["price", "stock", "Descuento"].includes(field) ? "number" : "text"}
                                        />
                                    </div>
                                ))}
                                <button className="btn btn-warning w-100 mt-2" onClick={handleAddProduct}>
                                    Agregar producto
                                </button>
                            </div>
                        )}
                    </main>
                </div>
            </div>
            <Footer />
        </>
    );
};

export default Misproductos;
