package main.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManagerServiceTest extends BaseServiceTest {
    @Test
    public void testCanDelete() {
        Assertions.assertFalse(managerService.canDelete(manager.getId()));

        appointmentRepository.delete(appointment);
        // Всё ещё нельзя удалять, т.к. услуга есть
        Assertions.assertFalse(managerService.canDelete(manager.getId()));

        productRepository.delete(product);
        // Уже можно
        Assertions.assertTrue(managerService.canDelete(manager.getId()));
    }
}
