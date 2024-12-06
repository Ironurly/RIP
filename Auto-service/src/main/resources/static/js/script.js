document.addEventListener('DOMContentLoaded', () => {
    // Элементы секций
    const authSection = document.getElementById('auth-section');
    const loginSection = document.getElementById('login-section');
    const registerSection = document.getElementById('register-section');

    // Кнопки для переключения между секциями
    const showLoginButton = document.getElementById('show-login');
    const showRegisterButton = document.getElementById('show-register');
    const backToAuthButton = document.getElementById('back-to-auth');
    const backToAuthRegButton = document.getElementById('back-to-auth-reg');

    // Формы
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');

    // Подвал
    const showContactsButton = document.getElementById('show-contacts');
    const contactsSection = document.getElementById('contacts-section');

    // Переключение видимости блока контактов
    showContactsButton?.addEventListener('click', () => {
        if (contactsSection.style.display === 'none') {
            contactsSection.style.display = 'block';
            showContactsButton.textContent = 'Скрыть контакты';
        } else {
            contactsSection.style.display = 'none';
            showContactsButton.textContent = 'Контакты';
        }
    });

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

    // Возврат к авторизации из секции входа
    backToAuthButton?.addEventListener('click', () => {
        loginSection.style.display = 'none';
        authSection.style.display = 'block';
    });

    // Возврат к авторизации из секции регистрации
    backToAuthRegButton?.addEventListener('click', () => {
        registerSection.style.display = 'none';
        authSection.style.display = 'block';
    });

    // Обработка формы регистрации
    registerForm?.addEventListener('submit', (event) => {
        event.preventDefault(); // Останавливаем стандартное поведение отправки формы

        const username = document.getElementById('register-username').value.trim();
        const password = document.getElementById('register-password').value.trim();

        fetch('/api/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        })
            .then(response => response.json().then(data => {
                if (response.ok) {
                    alert(data.message);
                    authSection.style.display = 'block';
                    registerSection.style.display = 'none';
                } else {
                    console.error('Ошибка регистрации:', data.message);
                    alert(data.message);
                }
            }))
            .catch(error => {
                console.error('Ошибка при регистрации:', error.message);
                alert('Произошла ошибка при регистрации. Попробуйте снова.');
            });
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
            body: JSON.stringify({ username, password })
        })
            .then(response => response.json().then(data => {
                if (response.ok) {
                    alert(data.message); // Сообщение об успехе
                    window.location.href = '/home'; // Перенаправляем на /home
                } else {
                    console.error('Ошибка входа:', data.message);
                    alert(data.message);
                }
            }))
            .catch(error => {
                console.error('Ошибка при входе:', error.message);
                alert('Произошла ошибка при входе. Попробуйте снова.');
            });
    });
});
