document.addEventListener('DOMContentLoaded', () => {
    // Элементы секций
    const authSection = document.getElementById('auth-section');
    const loginSection = document.getElementById('login-section');
    const registerSection = document.getElementById('register-section');
    const mainContent = document.getElementById('main-content');

    // Кнопки
    const showLoginButton = document.getElementById('show-login');
    const showRegisterButton = document.getElementById('show-register');
    const backToAuthButton = document.getElementById('back-to-auth');
    const backToAuthRegButton = document.getElementById('back-to-auth-reg');

    // Формы
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');

    // Показ секции входа
    showLoginButton?.addEventListener('click', () => {
        authSection.style.display = 'none';
        loginSection.style.display = 'block';
    });

    // Показ секции регистрации
    showRegisterButton?.addEventListener('click', () => {
        authSection.style.display = 'none';
        registerSection.style.display = 'block';
    });

    // Возврат к авторизации из входа
    backToAuthButton?.addEventListener('click', () => {
        loginSection.style.display = 'none';
        authSection.style.display = 'block';
    });

    // Возврат к авторизации из регистрации
    backToAuthRegButton?.addEventListener('click', () => {
        registerSection.style.display = 'none';
        authSection.style.display = 'block';
    });

    // Обработка формы регистрации
    registerForm?.addEventListener('submit', (event) => {
        event.preventDefault(); // Останавливаем стандартное поведение отправки формы

        const username = document.getElementById('register-username').value;
        const password = document.getElementById('register-password').value;

        fetch('http://localhost:8080/api/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        })
            .then(response => {
                console.log('HTTP Response Status:', response.status);
                console.log('HTTP Response Body:', response);
                if (!response.ok) {
                    return response.json().then(errorData => {
                        console.error('Error Response Data:', errorData);
                        throw new Error(errorData.message || 'Ошибка регистрации');
                    });
                }
                return response.json();
            })
            .then(data => {
                console.log('Success Response Data:', data);
                alert(data.message);
                authSection.style.display = 'block';
                registerSection.style.display = 'none';
            })
            .catch(error => console.error('Fetch Error:', error.message));

    });

    // Обработка формы входа
    loginForm?.addEventListener('submit', (event) => {
        event.preventDefault(); // Останавливаем стандартное поведение отправки формы

        const username = document.getElementById('login-username').value;
        const password = document.getElementById('login-password').value;

        fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                "username": username,
                "password": password
            })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert(data.message);
                    authSection.style.display = 'none';
                    mainContent.style.display = 'block';
                    document.getElementById('user-role').textContent = username;
                } else {
                    alert(data.message);
                }
            })
            .catch(error => console.error('Ошибка входа:', error));
    });
});
