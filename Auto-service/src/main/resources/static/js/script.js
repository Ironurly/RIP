// Регистрация пользователя
const registerForm = document.getElementById('register-form');
registerForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const username = document.getElementById('register-username').value;
    const password = document.getElementById('register-password').value;

    fetch('/api/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password })
    }).then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('Регистрация прошла успешно!');
                authSection.classList.remove('active');
                loginSection.classList.add('active');
            } else {
                alert(data.message || 'Ошибка при регистрации!');
            }
        }).catch(error => console.error('Ошибка:', error));
});

// Вход пользователя
const loginForm = document.getElementById('login-form');
loginForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;

    fetch('/api/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password })
    }).then(response => {
        if (response.ok) {
            return response.json();
        }
        throw new Error('Ошибка авторизации');
    }).then(data => {
        if (data.success) {
            mainContent.classList.add('active');
            authSection.classList.remove('active');
            document.getElementById('user-role').textContent = username;
            window.location.href = '/home'; // Перенаправление на главную страницу
        } else {
            alert('Неверный логин или пароль!');
        }
    }).catch(error => console.error('Ошибка:', error));
});
