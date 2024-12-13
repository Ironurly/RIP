package main.service;

import main.entity.User;
import main.utils.HashUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserServiceTest extends BaseServiceTest {
    @Test
    public void testRegister() {
        String login = "логин-который-ну-точно-не-существует";
        Assertions.assertNull(userService.findByLogin(login));

        userService.register(User.builder().login(login).phone("phone").name("name").password("password").build());

        User registered = userService.findByLogin(login);
        Assertions.assertNotNull(registered);
        Assertions.assertEquals(HashUtils.hash(login, "password"), registered.getPassword());
    }
}
