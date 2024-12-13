package main.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SessionServiceTest extends BaseServiceTest {
    @Test
    public void testLoginLogout() {
        Assertions.assertEquals(1, sessionRepository.findAll().size());

        sessionService.logout("user-login-1234567890");

        Assertions.assertEquals(0, sessionRepository.findAll().size());

        // Неправильный пароль
        sessionService.login("user-login-1234567890", "wrong-pass");

        Assertions.assertEquals(0, sessionRepository.findAll().size());

        // Правильный пароль
        sessionService.login("user-login-1234567890", "password");
        Assertions.assertEquals(1, sessionRepository.findAll().size());
    }

    @Test
    public void testCurrentUser() {
        Assertions.assertEquals(user.getId(), sessionService.getCurrentUser().getId());
    }
}
