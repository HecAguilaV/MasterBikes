// admin.js - Gestión de usuarios para admins

const API_URL = '/api/usuarios';
const tabla = document.querySelector('#usuariosTable tbody');
const mensajeDiv = document.getElementById('mensaje');

// Obtener el token del localStorage (ajusta según tu flujo de login)
const token = localStorage.getItem('token');

// Decodifica el JWT para validar el rol (solo admins pueden ver esta página)
function parseJwt (token) {
    try {
        return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
        return null;
    }
}

function mostrarMensaje(msg, tipo = 'success') {
    mensajeDiv.textContent = msg;
    mensajeDiv.className = tipo;
    setTimeout(() => mensajeDiv.textContent = '', 3000);
}

function cargarUsuarios() {
    fetch(API_URL, {
        headers: { 'Authorization': 'Bearer ' + token }
    })
    .then(res => res.json())
    .then(usuarios => {
        tabla.innerHTML = '';
        usuarios.forEach(u => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${u.id}</td>
                <td>${u.nombre}</td>
                <td>${u.email}</td>
                <td>
                    <select data-id="${u.id}" class="rol-select">
                        <option value="ADMIN" ${u.rol === 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                        <option value="VENDEDOR" ${u.rol === 'VENDEDOR' ? 'selected' : ''}>VENDEDOR</option>
                        <option value="CLIENTE" ${u.rol === 'CLIENTE' ? 'selected' : ''}>CLIENTE</option>
                    </select>
                </td>
                <td><button data-id="${u.id}" class="guardar-btn">Guardar</button></td>
            `;
            tabla.appendChild(tr);
        });
    })
    .catch(() => mostrarMensaje('Error al cargar usuarios', 'error'));
}

// Cambiar rol
function cambiarRol(id, nuevoRol) {
    fetch(`${API_URL}/${id}/rol`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        },
        body: JSON.stringify({ rol: nuevoRol })
    })
    .then(res => res.json().then(data => ({ status: res.status, data })))
    .then(({ status, data }) => {
        if (status === 200) {
            mostrarMensaje('Rol actualizado correctamente');
            cargarUsuarios();
        } else {
            mostrarMensaje(data.mensaje || data.error || 'Error al actualizar rol', 'error');
        }
    })
    .catch(() => mostrarMensaje('Error de red', 'error'));
}

// Validar acceso admin
(function validarAdmin() {
    if (!token) {
        window.location.href = '/pages/login.html';
        return;
    }
    const payload = parseJwt(token);
    if (!payload || payload.rol !== 'ADMIN') {
        alert('Acceso solo para administradores');
        window.location.href = '/';
    }
})();

// Delegación de eventos para guardar cambios de rol
tabla.addEventListener('click', function(e) {
    if (e.target.classList.contains('guardar-btn')) {
        const id = e.target.getAttribute('data-id');
        const select = tabla.querySelector(`select[data-id="${id}"]`);
        const nuevoRol = select.value;
        cambiarRol(id, nuevoRol);
    }
});

// Cargar usuarios al iniciar
cargarUsuarios();
