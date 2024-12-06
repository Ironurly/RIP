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

        fetch('/api/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
            "username": username,
            "password": password
            })
        })
            .then(response => {
                console.log(JSON.stringify({
                    "username": username,
                    "password": password
                }));
                console.log('Next');
                console.log(JSON.stringify({ username, password }));
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

        const username = document.getElementById('login-username').value.trim();
        const password = document.getElementById('login-password').value.trim();

        fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include', // Позволяет сохранять куки сессии
            body: JSON.stringify({
                username, // Упрощенная запись { "username": username }
                password
            })
        })
            .then(response => {
                // Проверяем статус ответа
                return response.json().then(data => {
                    if (response.ok) {
                        // Успешный вход
                        console.log('Вход успешен:', data);
                        alert(data.message); // Сообщение об успехе
                        window.location.href = '/home'; // Перенаправляем на /home
                    } else {
                        // Ошибка входа
                        console.error('Ошибка входа:', data.message);
                        alert(data.message); // Показываем сообщение об ошибке
                    }
                });
            })
            .catch(error => {
                console.error('Fetch Error:', error.message);
                alert('Произошла ошибка при входе. Попробуйте снова.');
            });
    });
});
