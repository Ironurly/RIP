package main.service;

import lombok.RequiredArgsConstructor;
import main.TestUtils;
import main.entity.*;
import main.repository.*;
import main.utils.DateUtils;
import main.utils.HashUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BaseServiceTest {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ManagerRepository managerRepository;
    @Autowired
    protected SessionRepository sessionRepository;
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected AppointmentRepository appointmentRepository;

    protected UserService userService;
    protected ManagerService managerService;
    protected SessionService sessionService;
    protected ProductService productService;
    protected AppointmentService appointmentService;

    protected User user = new User();
    protected Session session = new Session();
    protected Manager manager = new Manager();
    protected Product product = new Product();
    protected Appointment appointment = new Appointment();

    @BeforeEach
    public void beforeEach() {
        initializeFields();

        sessionRepository.deleteAll();

        user = User.builder()
                .login("user-login-1234567890")
                .password(HashUtils.hash("user-login-1234567890", "password"))
                .name("user-name")
                .phone("user-phone")
                .build();
        user = userRepository.save(user);

        session = Session.builder()
                .userId(user.getId())
                .build();
        session = sessionRepository.save(session);

        manager = Manager.builder()
                .name("manager-name")
                .description("manager-description")
                .build();
        manager = managerRepository.save(manager);

        product = Product.builder()
                .cost(100)
                .name("product-name")
                .managerId(manager.getId())
                .duration(60)
                .build();
        product = productRepository.save(product);

        appointment = Appointment.builder()
                .productId(product.getId())
                .carNumber("number")
                .carBrand("brand")
                .userId(user.getId())
                .startTime(DateUtils.parse("2020-01-01T00:00"))
                .build();
        appointment = appointmentRepository.save(appointment);
    }

    @AfterEach
    public void afterEach() {
        userRepository.delete(user);
        sessionRepository.delete(session);
        managerRepository.delete(manager);
        productRepository.delete(product);
        appointmentRepository.delete(appointment);
    }

    private void initializeFields() {
        userService = new UserService(userRepository);

        managerService = new ManagerService(
                managerRepository,
                productRepository,
                appointmentRepository
        );

        sessionService = new SessionService(
                sessionRepository,
                userService
        );

        productService = new ProductService(
                productRepository,
                managerRepository,
                appointmentRepository
        );

        appointmentService = new AppointmentService(
                appointmentRepository,
                managerService,
                productService,
                sessionService,
                userService
        );
    }
}
