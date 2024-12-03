document.addEventListener('DOMContentLoaded', () => {
    const authSection = document.getElementById('auth-section');
    const loginSection = document.getElementById('login-section');
    const registerSection = document.getElementById('register-section');
    const mainContent = document.getElementById('main-content');

    const showLoginButton = document.getElementById('show-login');
    const showRegisterButton = document.getElementById('show-register');
    const continueGuestButton = document.getElementById('continue-guest');

    const backToAuthFromLogin = document.getElementById('back-to-auth');
    const backToAuthFromRegister = document.getElementById('back-to-auth-reg');

    // Переход к форме входа
    showLoginButton.addEventListener('click', () => {
        authSection.classList.remove('active');
        loginSection.classList.add('active');
    });

    // Переход к форме регистрации
    showRegisterButton.addEventListener('click', () => {
        authSection.classList.remove('active');
        registerSection.classList.add('active');
    });

    // Продолжить как гость
    continueGuestButton.addEventListener('click', () => {
        mainContent.classList.add('active');
        authSection.classList.remove('active');
        document.getElementById('user-role').textContent = 'гость';
    });

    // Возврат к форме авторизации из формы входа
    backToAuthFromLogin.addEventListener('click', () => {
        loginSection.classList.remove('active');
        authSection.classList.add('active');
    });

    // Возврат к форме авторизации из формы регистрации
    backToAuthFromRegister.addEventListener('click', () => {
        registerSection.classList.remove('active');
        authSection.classList.add('active');
    });

    // Регистрация пользователя
    const registerForm = document.getElementById('register-form');
    registerForm.addEventListener('submit', (event) => {
        event.preventDefault();
        const username = document.getElementById('register-username').value;
        const password = document.getElementById('register-password').value;

        fetch('/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        }).then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('Регистрация прошла успешно!');
                    window.location.href = '/login';  // Перенаправление на страницу входа
                } else {
                    alert('Ошибка при регистрации!');
                }
            }).catch(error => console.error('Ошибка:', error));
    });

    // Вход пользователя
    const loginForm = document.getElementById('login-form');
    loginForm.addEventListener('submit', (event) => {
        event.preventDefault();
        const username = document.getElementById('login-username').value;
        const password = document.getElementById('login-password').value;

        fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        }).then(response => response.json())
            .then(data => {
                if (data.success) {
                    mainContent.classList.add('active');
                    authSection.classList.remove('active');
                    document.getElementById('user-role').textContent = username;
                } else {
                    alert('Неверный логин или пароль!');
                }
            }).catch(error => console.error('Ошибка:', error));
    });
});
