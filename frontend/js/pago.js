// Inicializar EmailJS
emailjs.init('dF-ZSyQCC3Jx91Pzh');

// Referencias a elementos del DOM
const cart = JSON.parse(localStorage.getItem('cart') || '[]');
const orderSummary = document.getElementById('orderSummary');
const summaryTotal = document.getElementById('summaryTotal');
const cartBadge = document.querySelector('.cart-badge');
const paymentForm = document.getElementById('paymentForm');

// Formatear precios en CLP
function formatPrice(price) {
  return new Intl.NumberFormat('es-CL', {
    style: 'currency',
    currency: 'CLP'
  }).format(price);
}

// Mostrar el número total de productos en el ícono del carrito
function renderBadge() {
  const totalItems = cart.reduce((sum, item) => sum + item.quantity, 0);
  if (cartBadge) {
    cartBadge.textContent = totalItems;
    cartBadge.style.display = totalItems ? 'block' : 'none';
  }
}

// Mostrar resumen del pedido en la página
function renderSummary() {
  let total = 0;
  orderSummary.innerHTML = '';

  if (!cart.length) {
    orderSummary.innerHTML = '<p>No hay productos en tu pedido.</p>';
    summaryTotal.textContent = '$0';
    return;
  }

  cart.forEach(item => {
    const itemTotal = item.price * item.quantity;
    total += itemTotal;

    orderSummary.innerHTML += `
      <div class="d-flex justify-content-between border-bottom py-2">
        <div><strong>${item.name}</strong><br><small>Cantidad: ${item.quantity}</small></div>
        <div>${formatPrice(itemTotal)}</div>
      </div>
    `;
  });

  summaryTotal.textContent = formatPrice(total);
}

// Ejecutar al cargar el DOM
document.addEventListener('DOMContentLoaded', () => {
  renderSummary();
  renderBadge();
});

// Enviar formulario de pago
paymentForm.addEventListener('submit', e => {
  e.preventDefault();

  if (!paymentForm.checkValidity()) {
    paymentForm.classList.add('was-validated');
    return;
  }

  // Ocultar sección de pago y mostrar éxito
  localStorage.removeItem('cart');
  document.getElementById('checkoutSection').classList.add('d-none');
  document.getElementById('successSection').classList.remove('d-none');

  // Re-render badge y resumen vacío
  renderBadge();
  renderSummary();

  // Preparar datos del formulario
  const formData = new FormData(paymentForm);
  const nombre = formData.get('nombre');
  const correo = formData.get('correo');

  // Calcular total y generar HTML del pedido
  const total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

  const itemsHtml = cart.map(item => `
    <li>${item.quantity} x ${item.name} — ${formatPrice(item.price * item.quantity)}</li>
  `).join('');

  const message = `
    <p>Gracias por tu compra en <strong>MasterBikes</strong>.</p>
    <p><strong>Detalle del pedido:</strong></p>
    <ul>${itemsHtml}</ul>
    <p><strong>Total: ${formatPrice(total)}</strong></p>
  `;

  // Enviar correo con EmailJS
  emailjs.send('service_90bp5de', 'template_y90jv2d', {
    to_name: nombre,
    to_email: correo,
    message: message
  }).then(() => {
    console.log('Correo enviado correctamente');
  }).catch(err => {
    console.error('Error al enviar correo:', err);
    alert('No se pudo enviar el correo: ' + err.text);
  });
});
