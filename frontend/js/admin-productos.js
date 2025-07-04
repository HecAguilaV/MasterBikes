// admin-productos.js

const API_BASE_URL = "http://localhost:9000";
const token = localStorage.getItem('token');

function mostrarAlerta(msg, tipo = 'success') {
    const alertDiv = document.getElementById('admin-alert');
    alertDiv.textContent = msg;
    alertDiv.className = `alert alert-${tipo === 'success' ? 'success' : 'danger'}`;
    alertDiv.classList.remove('d-none');
    setTimeout(() => alertDiv.classList.add('d-none'), 3500);
}

function parseJwt(token) {
    try {
        return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
        return null;
    }
}

(function validarAdmin() {
    if (!token) {
        window.location.href = '/frontend/pages/login.html';
        return;
    }
    const payload = parseJwt(token);
    if (!payload || payload.rol !== 'ADMIN') {
        alert('Acceso solo para administradores');
        window.location.href = '/';
    }
})();

// Agregar componente
const formComponente = document.getElementById('form-componente');
formComponente.onsubmit = async function(e) {
    e.preventDefault();
    const data = {
        tipo: this.tipo.value,
        marca: this.marca.value,
        modelo: this.modelo.value,
        precioUnitario: parseInt(this.precioUnitario.value)
    };
    try {
        const res = await fetch(`${API_BASE_URL}/api/v1/catalogo/componentes`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify(data)
        });
        if (res.ok) {
            mostrarAlerta('Componente agregado correctamente');
            formComponente.reset();
        } else {
            const err = await res.json();
            mostrarAlerta(err.mensaje || 'Error al agregar componente', 'error');
        }
    } catch {
        mostrarAlerta('Error de red', 'error');
    }
};

// Agregar bicicleta
const formBicicleta = document.getElementById('form-bicicleta');
formBicicleta.onsubmit = async function(e) {
    e.preventDefault();
    const data = {
        modelo: this.modeloBici.value,
        tallaUsuario: this.tallaUsuario.value,
        idMarco: parseInt(this.idMarco.value),
        idRueda: parseInt(this.idRueda.value),
        idFreno: parseInt(this.idFreno.value),
        idManubrio: parseInt(this.idManubrio.value),
        idSillin: parseInt(this.idSillin.value),
        esPredefinida: this.esPredefinida.value === 'true'
    };
    try {
        const res = await fetch(`${API_BASE_URL}/api/v1/catalogo/bicicletas`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify(data)
        });
        if (res.ok) {
            mostrarAlerta('Bicicleta agregada correctamente');
            formBicicleta.reset();
        } else {
            const err = await res.json();
            mostrarAlerta(err.mensaje || 'Error al agregar bicicleta', 'error');
        }
    } catch {
        mostrarAlerta('Error de red', 'error');
    }
};
