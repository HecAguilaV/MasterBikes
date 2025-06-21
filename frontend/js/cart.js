const bikeData = [
    {id: 1, type: 'Montaña', name: 'Orion 4 Titanio', brand: 'Oxford', size: '29', price: 15000, img: '../images/ba2961-orion-4-29-titanio-2022-02.jpg', discount: 15, availability: 'available', rating: 4.7},
    {id: 2, type: 'Montaña', name: 'Halley Pro', brand: 'Shimano', size: '27.5', price: 12000, img: '../images/mtb.webp', discount: 20, availability: 'available', rating: 4.5},
    {id: 3, type: 'Montaña', name: 'Merak 1 Elite', brand: 'Oxford', size: '29', price: 18000, img: '../images/mtb_2.webp', discount: 10, availability: 'available', rating: 4.8},
    {id: 4, type: 'Montaña', name: 'Aura 6 Carbon', brand: 'Oxford', size: '27.5', price: 25000, img: '../images/pendiente.jpg', discount: 25, availability: 'reserved', rating: 4.9},
    {id: 5, type: 'Montaña', name: 'Pollux 7 Race', brand: 'Shimano', size: '29', price: 20000, img: '../images/pendiente.jpg', discount: 30, availability: 'available', rating: 4.6},
    {id: 6, type: 'Montaña', name: 'Luna 20 Kids', brand: 'Oxford', size: '20', price: 8000, img: '../images/pendiente.jpg', discount: 18, availability: 'available', rating: 4.3},
    {id: 7, type: 'Urbana', name: 'City Cruiser', brand: 'Oxford', size: '27', price: 10000, img: '../images/pendiente.jpg', discount: 22, availability: 'available', rating: 4.4},
    {id: 8, type: 'Eléctrica', name: 'E-Bike Pro', brand: 'Shimano', size: '-', price: 35000, img: '../images/pendiente.jpg', discount: 12, availability: 'reserved', rating: 4.7},
    {id: 9, type: 'Montaña', name: 'Mini Trail', brand: 'Oxford', size: '24', price: 7000, img: '../images/pendiente.jpg', discount: 17, availability: 'available', rating: 4.2},
    {id: 10, type: 'Ruta', name: 'Speed Master', brand: 'Shimano', size: '29', price: 22000, img: '../images/pendiente.jpg', discount: 20, availability: 'available', rating: 4.8},
    {id: 11, type: 'Montaña', name: 'Classic MTB', brand: 'Oxford', size: '26', price: 9000, img: '../images/pendiente.jpg', discount: 15, availability: 'available', rating: 4.1},
    {id: 12, type: 'BMX', name: 'Freestyle Pro', brand: 'Giant', size: '20', price: 6000, img: '../images/pendiente.jpg', discount: 8, availability: 'available', rating: 4.6}
];

class Cart {
    constructor() {
        this.cart = JSON.parse(localStorage.getItem('masterBikesCart')) || [];
        this.init();
    }

    init() {
        this.updateCartUI();
        document.addEventListener('click', (e) => {
            if (e.target.closest('.add-to-cart-btn')) {
                const bikeId = parseInt(e.target.closest('.add-to-cart-btn').dataset.id);
                this.add(bikeId);
            }
            if (e.target.closest('.cart-item-remove')) {
                const bikeId = parseInt(e.target.closest('.cart-item-remove').dataset.id);
                this.remove(bikeId);
            }
        });
    }

    add(bikeId) {
        if (this.cart.some(item => item.id === bikeId)) {
            console.log("Bike already in cart.");
            return;
        }
        const bike = bikeData.find(b => b.id === bikeId);
        if (bike) {
            this.cart.push({ ...bike, quantity: 1 });
            this.save();
            this.updateCartUI();
            this.showToast(`¡"${bike.name}" se agregó al carrito!`);
        }
    }

    remove(bikeId) {
        const bikeName = this.cart.find(item => item.id === bikeId)?.name || 'El producto';
        this.cart = this.cart.filter(item => item.id !== bikeId);
        this.save();
        this.updateCartUI();
        this.showToast(`"${bikeName}" se eliminó del carrito.`, 'error');
    }

    save() {
        localStorage.setItem('masterBikesCart', JSON.stringify(this.cart));
    }

    updateCartUI() {
        const cartBadge = document.getElementById('cart-badge');
        const cartItemsContainer = document.getElementById('cart-items-container');
        const cartEmptyMsg = document.getElementById('cart-empty-msg');
        const cartFooter = document.getElementById('cart-footer');
        const cartTotalEl = document.getElementById('cart-total');

        if (!cartBadge) return; // Exit if cart elements are not on the page

        // Update badge
        if (this.cart.length > 0) {
            cartBadge.textContent = this.cart.length;
            cartBadge.style.display = 'block';
        } else {
            cartBadge.style.display = 'none';
        }

        // Update off-canvas body
        if (this.cart.length === 0) {
            cartItemsContainer.innerHTML = '<p class="text-center text-muted" id="cart-empty-msg">Tu carrito está vacío.</p>';
            cartFooter.style.display = 'none';
        } else {
            cartItemsContainer.innerHTML = '';
            let total = 0;
            this.cart.forEach(item => {
                const cartItemEl = document.createElement('div');
                cartItemEl.className = 'cart-item';
                cartItemEl.innerHTML = `
                    <img src="${item.img.replace('../', './')}" alt="${item.name}" class="cart-item-img">
                    <div class="cart-item-details">
                        <h6>${item.name}</h6>
                        <p class="text-muted mb-1">${item.brand}</p>
                        <p class="cart-item-price">${this.formatPrice(item.price)}</p>
                    </div>
                    <button class="cart-item-remove" data-id="${item.id}" title="Eliminar">&times;</button>
                `;
                cartItemsContainer.appendChild(cartItemEl);
                total += item.price * item.quantity;
            });
            cartTotalEl.textContent = this.formatPrice(total);
            cartFooter.style.display = 'block';
        }
        
        // Update bike card buttons on the current page
        document.querySelectorAll('.add-to-cart-btn').forEach(btn => {
            const bikeId = parseInt(btn.dataset.id);
            const bike = bikeData.find(b => b.id === bikeId);

            if (this.cart.some(item => item.id === bikeId)) {
                btn.innerHTML = '<i class="bi bi-check-lg me-2"></i>En el Carrito';
                btn.disabled = true;
                btn.classList.remove('btn-primary');
                btn.classList.add('btn-success');
            } else {
                if (bike && bike.availability === 'available') {
                    btn.innerHTML = '<i class="bi bi-cart-plus me-2"></i>Agregar al Carrito';
                    btn.disabled = false;
                    btn.classList.add('btn-primary');
                    btn.classList.remove('btn-success');
                } else {
                     btn.innerHTML = '<i class="bi bi-x-circle me-2"></i>No Disponible';
                     btn.disabled = true;
                }
            }
        });
    }

    formatPrice(price) {
        return new Intl.NumberFormat('es-CL', { style: 'currency', currency: 'CLP' }).format(price);
    }

    showToast(message, type = 'success') {
        let toastContainer = document.getElementById('toast-container');
        if (!toastContainer) {
            const container = document.createElement('div');
            container.id = 'toast-container';
            container.style.position = 'fixed';
            container.style.bottom = '20px';
            container.style.right = '20px';
            container.style.zIndex = '1055';
            document.body.appendChild(container);
            toastContainer = container;
        }

        const toast = document.createElement('div');
        toast.className = `toast align-items-center text-white bg-${type === 'success' ? 'primary' : 'danger'} border-0 show`;
        toast.setAttribute('role', 'alert');
        toast.setAttribute('aria-live', 'assertive');
        toast.setAttribute('aria-atomic', 'true');

        toast.innerHTML = `
            <div class="d-flex">
                <div class="toast-body">
                    ${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        `;

        toastContainer.appendChild(toast);
        
        const bsToast = new bootstrap.Toast(toast, { delay: 3000 });
        bsToast.show();
        
        toast.addEventListener('hidden.bs.toast', () => {
            toast.remove();
        });
    }
}

document.addEventListener('DOMContentLoaded', () => {
    window.cart = new Cart();
});