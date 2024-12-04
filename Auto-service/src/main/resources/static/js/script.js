document.addEventListener('DOMContentLoaded', () => {
    // Элементы секций
    const authSection = document.getElementById('auth-section');
    const loginSection = document.getElementById('login-section');
    const registerSection = document.getElementById('register-section');
    const mainContent = document.getElementById('main-content');

    // Кнопки
    const showLoginButton = document.getElementById('show-login');
    const showRegisterButton = document.getElementById('show-register');
    const continueGuestButton = document.getElementById('continue-guest');
    const backToAuthButton = document.getElementById('back-to-auth');
    const backToAuthRegButton = document.getElementById('back-to-auth-reg');

    // Показ секции входа
    showLoginButton?.addEventListener('click', () => {
        authSection.classList.remove('active');
        loginSection.classList.add('active');
    });

    // Показ секции регистрации
    showRegisterButton?.addEventListener('click', () => {
        authSection.classList.remove('active');
        registerSection.classList.add('active');
    });

    // Возврат к выбору способа входа
    backToAuthButton?.addEventListener('click', () => {
        loginSection.classList.remove('active');
        authSection.classList.add('active');
    });

    backToAuthRegButton?.addEventListener('click', () => {
        registerSection.classList.remove('active');
        authSection.classList.add('active');
    });

    // Продолжить как гость
    continueGuestButton?.addEventListener('click', () => {
        authSection.classList.remove('active');
        mainContent.classList.add('active');
        document.getElementById('user-role').textContent = 'гость';
    });
});
