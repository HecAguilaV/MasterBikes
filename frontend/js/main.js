// Manejo de formularios y conexión real con backend
// Corrige rutas, nombres de campos y asegura integración con backend

document.addEventListener('DOMContentLoaded', function() {
    // LOGIN
    const loginForm = document.querySelector('#loginModal form');
    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const email = document.getElementById('loginEmail').value;
            const password = document.getElementById('loginPassword').value;

            // Validación básica
            if (!validarEmail(email)) {
                alert('Por favor, ingresa un email válido.');
                return;
            }
            if (!validarPassword(password)) {
                alert('La contraseña debe tener al menos 8 caracteres.');
                return;
            }

            try {
                const response = await fetch('http://localhost:8081/auth/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ email, password })
                });
                if (response.ok) {
                    const data = await response.json();
                    localStorage.setItem('token', data.token);
                    alert('Inicio de sesión exitoso. ¡Bienvenido!');
                    bootstrap.Modal.getInstance(document.getElementById('loginModal')).hide();
                    // Aquí puedes redirigir o actualizar la UI
                } else {
                    alert('Credenciales inválidas.');
                }
            } catch (error) {
                alert('Error al iniciar sesión.');
            }
        });
    }

    // REGISTRO
    const registroForm = document.querySelector('#registroModal form');
    if (registroForm) {
        registroForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const nombre = document.getElementById('registerName').value;
            const email = document.getElementById('registerEmail').value;
            const password = document.getElementById('registerPassword').value;
            const telefono = document.getElementById('registerPhone').value;
            const direccion = document.getElementById('registerAddress').value;
            const fechaNacimiento = document.getElementById('registerBirthdate').value;

            if (!nombre || !email || !password) {
                alert('Por favor, completa todos los campos requeridos.');
                return;
            }
            if (!validarEmail(email)) {
                alert('Por favor, ingresa un email válido.');
                return;
            }
            if (!validarPassword(password)) {
                alert('La contraseña debe tener al menos 8 caracteres.');
                return;
            }

            try {
                const response = await fetch('http://localhost:8081/api/usuarios/registro', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ nombre, email, password, telefono, direccion, fechaNacimiento })
                });
                if (response.ok) {
                    alert('Registro exitoso. Ahora puedes iniciar sesión.');
                    bootstrap.Modal.getInstance(document.getElementById('registroModal')).hide();
                    bootstrap.Modal.getOrCreateInstance(document.getElementById('loginModal')).show();
                } else {
                    const data = await response.json();
                    alert(data.mensaje || 'Error en el registro');
                }
            } catch (error) {
                alert('Error al registrar.');
            }
        });
    }

    // Selección de sucursal (puede venir del usuario logueado o permitir elegir)
    let sucursalSeleccionada = null;
    const user = sessionManager.getCurrentUser();
    if (user && user.sucursal) {
        sucursalSeleccionada = user.sucursal;
    } else {
        // Si no hay sucursal en usuario, puedes mostrar un selector en la UI y guardar la selección
        // Ejemplo: sucursalSeleccionada = 'SUCURSAL_1';
    }

    // Cargar catálogo y stock real
    async function cargarCatalogo() {
        try {
            const response = await fetch('http://localhost:8082/api/catalogo');
            if (response.ok) {
                const productos = await response.json();
                // Consultar stock para cada producto
                const productosConStock = await Promise.all(productos.map(async (prod) => {
                    const stock = await consultarStock(prod.id, sucursalSeleccionada);
                    return { ...prod, stock };
                }));
                renderizarCatalogo(productosConStock);
            } else {
                alert('No se pudo cargar el catálogo.');
            }
        } catch (error) {
            alert('Error al conectar con el catálogo.');
        }
    }

    // Consultar stock desde inventario-service
    async function consultarStock(productoId, sucursal) {
        try {
            const url = `http://localhost:8084/api/inventario/stock?productoId=${productoId}&sucursal=${sucursal || ''}`;
            const response = await fetch(url);
            if (response.ok) {
                const data = await response.json();
                return data.stock;
            }
        } catch (e) {}
        return 0;
    }

    // Renderiza productos en la página de catálogo
    function renderizarCatalogo(productos) {
        const contenedor = document.getElementById('catalogoProductos');
        if (!contenedor) return;
        contenedor.innerHTML = '';
        productos.forEach(prod => {
            // Sucursal: solo bicicletas armadas
            if (sucursalSeleccionada && sucursalSeleccionada !== 'CASA_MATRIZ' && !prod.armada) return;
            contenedor.innerHTML += `<div class="producto card col-md-4 mb-4">
                <div class="card-body">
                    <h5 class="card-title">${prod.nombre}</h5>
                    <p class="card-text">${prod.descripcion}</p>
                    <p class="card-text"><b>Stock:</b> ${prod.stock > 0 ? prod.stock : 'Agotado'}</p>
                    <button class="btn btn-primary" onclick="comprarProducto('${prod.id}')" ${prod.stock <= 0 ? 'disabled' : ''}>Comprar</button>
                </div>
            </div>`;
        });
    }

    // Comprar producto (POST a ventas)
    window.comprarProducto = async function(productoId) {
        const token = localStorage.getItem('token');
        if (!token) {
            alert('Debes iniciar sesión para comprar.');
            return;
        }
        try {
            const response = await fetch('http://localhost:8085/api/ventas', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                },
                body: JSON.stringify({ productoId, sucursal: sucursalSeleccionada })
            });
            if (response.ok) {
                alert('Compra realizada con éxito.');
                cargarCatalogo(); // Actualiza stock
            } else {
                alert('No se pudo realizar la compra.');
            }
        } catch (error) {
            alert('Error al conectar con ventas.');
        }
    }

    // Llama cargarCatalogo() en la página de catálogo
    if (window.location.pathname.includes('catalogo.html')) {
        cargarCatalogo();
    }
});

// Validaciones
function validarEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

function validarPassword(password) {
    return password.length >= 8;
}