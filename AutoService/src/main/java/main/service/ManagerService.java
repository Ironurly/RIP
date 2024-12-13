package main.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import main.entity.Appointment;
import main.entity.Manager;
import main.entity.Product;
import main.repository.AppointmentRepository;
import main.repository.ManagerRepository;
import main.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Builder
public class ManagerService extends BaseService<Manager> {
    private final ManagerRepository managerRepository;
    private final ProductRepository productRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public JpaRepository<Manager, Long> getRepository() {
        return managerRepository;
    }

    /**
     * Определяет, можно ли удалять исполнителя, т.е. не привязаны ли к нему услуги и/или записи.
     * @param id идентификатор исполнителя
     * @return {@code true} если можно удалить исполнителя, {@code false} иначе.
     */
    public boolean canDelete(Long id) {
        List<Long> products = productRepository.findByManagerId(id).stream().map(Product::getId).collect(Collectors.toList());
        if (!products.isEmpty()) {
            return false;
        }
        return appointmentRepository.findByProductIdIn(products).isEmpty();
    }
}
