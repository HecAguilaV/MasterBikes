// frontend/js/api.js

// Cambia esta URL base según la configuración real de tu API Gateway
const API_BASE_URL = "http://localhost:9000"; // Puerto 9000 según application.yml

const API = {
    // Obtener productos del catálogo
    async getProductos() {
        const response = await fetch(`${API_BASE_URL}/catalogo/productos`);
        if (!response.ok) throw new Error("Error al obtener productos");
        return response.json();
    },

    // Obtener stock de una bicicleta por ID
    async getStockBicicleta(idBicicleta) {
        const response = await fetch(`${API_BASE_URL}/inventario/stock/${idBicicleta}`);
        if (!response.ok) throw new Error("Error al obtener stock");
        return response.json();
    },

    // Obtener sucursales
    async getSucursales() {
        const response = await fetch(`${API_BASE_URL}/sucursal/sucursales`);
        if (!response.ok) throw new Error("Error al obtener sucursales");
        return response.json();
    },

    // Login de usuario
    async login(email, password) {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });
        if (!response.ok) throw new Error("Credenciales inválidas");
        return response.json();
    },

    // Registro de usuario
    async register(userData) {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(userData)
        });
        if (!response.ok) throw new Error("Error al registrar usuario");
        return response.json();
    },

    // Puedes agregar más métodos según tus endpoints...
};

// Hacer disponible el objeto API globalmente
window.API = API;
