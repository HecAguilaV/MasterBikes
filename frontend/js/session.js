// Session Management System for MasterBikes
class SessionManager {
    constructor() {
        this.user = null;
        this.token = null;
        this.roles = [];
        this.init();
    }

    init() {
        // Check if user is already logged in
        const savedUser = localStorage.getItem('masterbikes_user');
        const savedToken = localStorage.getItem('masterbikes_token');
        const savedRoles = localStorage.getItem('masterbikes_roles');
        if (savedUser && savedToken) {
            this.user = JSON.parse(savedUser);
            this.token = savedToken;
            this.roles = savedRoles ? JSON.parse(savedRoles) : [];
            this.updateUI();
        }
    }

    handleLogin(authData) {
        // authData: { user, token, roles }
        this.user = authData.user || null;
        this.token = authData.token || null;
        this.roles = authData.roles || [];
        localStorage.setItem('masterbikes_user', JSON.stringify(this.user));
        localStorage.setItem('masterbikes_token', this.token);
        localStorage.setItem('masterbikes_roles', JSON.stringify(this.roles));
        this.updateUI();
    }

    logout() {
        this.user = null;
        this.token = null;
        this.roles = [];
        localStorage.removeItem('masterbikes_user');
        localStorage.removeItem('masterbikes_token');
        localStorage.removeItem('masterbikes_roles');
        this.updateUI();
        location.reload();
    }

    updateUI() {
        const loginBtn = document.querySelector('[data-bs-target="#loginModal"]');
        const registerBtn = document.querySelector('[data-bs-target="#registroModal"]');
        const userMenu = document.getElementById('userMenu');

        if (this.user) {
            // User is logged in
            if (loginBtn) loginBtn.style.display = 'none';
            if (registerBtn) registerBtn.style.display = 'none';
            if (userMenu) {
                userMenu.style.display = 'block';
                const userName = userMenu.querySelector('.user-name');
                if (userName) userName.textContent = this.user.name;
            }
        } else {
            // User is not logged in
            if (loginBtn) loginBtn.style.display = 'inline-block';
            if (registerBtn) registerBtn.style.display = 'inline-block';
            if (userMenu) {
                userMenu.style.display = 'none';
            }
        }
    }

    isLoggedIn() {
        return this.user !== null && this.token !== null;
    }

    getCurrentUser() {
        return this.user;
    }

    getToken() {
        return this.token;
    }

    getRoles() {
        return this.roles;
    }
}

// Initialize session manager
const sessionManager = new SessionManager();

// Add logout functionality
document.addEventListener('DOMContentLoaded', function() {
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function(e) {
            e.preventDefault();
            sessionManager.logout();
        });
    }
});
