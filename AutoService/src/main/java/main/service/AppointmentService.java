package main.service;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import main.dto.AppointmentDto;
import main.dto.CriteriaDto;
import main.entity.Appointment;
import main.entity.Manager;
import main.entity.Product;
import main.entity.User;
import main.repository.*;
import main.utils.DateUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Builder
public class AppointmentService extends BaseService<Appointment> {
    private final AppointmentRepository appointmentRepository;

    private final ManagerService managerService;
    private final ProductService productService;
    private final SessionService sessionService;
    private final UserService userService;

    @Override
    public JpaRepository<Appointment, Long> getRepository() {
        return appointmentRepository;
    }

    /**
     * @return все записи текущего пользователя
     */
    public List<AppointmentDto> findCurrentUserAppointments() {
        return appointmentRepository.findByUserId(sessionService.getCurrentUser().getId()).stream()
                .map(this::mapToAppointmentDto)
                .collect(Collectors.toList());
    }

    /**
     * Конвертирует технический объект типа {@code Appointment} в DTO с человекочитаемыми полями.
     * @param appointment объект типа {@code Appointment}
     * @return объект типа {@code AppointmentDto}
     */
    public AppointmentDto mapToAppointmentDto(Appointment appointment) {
        Optional<Product> product = productService.findById(appointment.getProductId());
        Optional<Manager> manager = product.map(Product::getManagerId).flatMap(managerService::findById);
        Optional<User> user = Optional.ofNullable(appointment.getUserId()).flatMap(userService::findById);

        AppointmentDto appointmentDto = new AppointmentDto();

        // Основные поля
        appointmentDto.setId(appointment.getId());
        appointmentDto.setUserId(appointment.getUserId());
        appointmentDto.setProductId(appointment.getProductId());
        appointmentDto.setCarBrand(appointment.getCarBrand());
        appointmentDto.setCarNumber(appointment.getCarNumber());
        appointmentDto.setStartTime(appointment.getStartTime());

        // Поля для отображения на фронт
        appointmentDto.setProductName(product.map(Product::getName).orElse(null));
        appointmentDto.setStartTimeStr(DateUtils.toString(appointment.getStartTime()));
        appointmentDto.setUserFio(user.map(User::getName).orElse(null));
        appointmentDto.setUserPhone(user.map(User::getPhone).orElse(null));
        appointmentDto.setCost(product.map(Product::getCost).orElse(null));
        appointmentDto.setDuration(product.map(Product::getDuration).orElse(null));
        appointmentDto.setManagerName(manager.map(Manager::getName).orElse(null));

        return appointmentDto;
    }

    /**
     * Конвертирует DTO записи с человекочитаемыми полями в технический объект типа {@code Appointment}. .
     * @param appointmentDto объект типа {@code AppointmentDto}
     * @return объект типа {@code Appointment}
     */
    public Appointment mapToAppointment(AppointmentDto appointmentDto) throws ParseException {
        return Appointment.builder()
                .id(appointmentDto.getId())
                .userId(appointmentDto.getUserId())
                .productId(appointmentDto.getProductId())
                .carNumber(appointmentDto.getCarNumber())
                .carBrand(appointmentDto.getCarBrand())
                .startTime(DateUtils.parse(appointmentDto.getStartTimeStr()))
                .build();
    }

    /**
     * Создаёт макет записи по идентификатору и/или идентификатору продукта:
     * <br />
     * <ul>
     *     <li>Если {@code id} ссылается на валидную запись, то подтягивается эта запись.</li>
     *     <li>Если {@code productId} ссылается на валидную услугу, то подтягиваются данные этой услуги.</li>
     * </ul>
     * @param id идентификатор записи
     * @param productId идентификатор продукта
     * @return макет записи
     */
    public AppointmentDto createDto(@Nullable Long id, @Nullable Long productId) {
        Appointment appointment = Objects.equals(id, -1L)
                ? new Appointment()
                : findById(id).orElseThrow();
        if (Objects.nonNull(productId)) {
            appointment.setProductId(productId);
        }
        if (Objects.isNull(appointment.getUserId())) {
            appointment.setUserId(sessionService.getCurrentUser().getId());
        }
        return mapToAppointmentDto(appointment);
    }

    /**
     * Отбирает записи по критериям.
     * @param criteriaDto критерии отбора
     * @return отобранные записи
     */
    public List<AppointmentDto> findByCriteria(CriteriaDto criteriaDto) {
        return appointmentRepository.findAll().stream()
                .filter(a -> matches(a, criteriaDto))
                .map(this::mapToAppointmentDto)
                .collect(Collectors.toList());
    }

    /**
     * Определяет, соответствует ли запись критериям. В критериях игнорируются {@code null}-поля
     * и -1 в роли {@code id}.
     *
     * @param appointment запись
     * @param criteria критерии отбора
     * @return {@code true} если запись соответствует критериям, {@code false} в противном случае
     */
    public boolean matches(Appointment appointment, CriteriaDto criteria) {
        boolean ok = true;
        if (Objects.nonNull(criteria.getManagerId()) && !Objects.equals(-1L, criteria.getManagerId())) {
            Product product = productService.findById(appointment.getProductId()).orElseThrow();
            ok &= Objects.equals(product.getManagerId(), criteria.getManagerId());
        }
        User user = userService.findById(appointment.getUserId()).orElseThrow();
        if (!StringUtils.isBlank(criteria.getClientFio())) {
            ok &= user.getName().contains(criteria.getClientFio());
        }
        if (!StringUtils.isBlank(criteria.getClientPhone())) {
            ok &= user.getPhone().contains(criteria.getClientPhone());
        }
        Date fromDate = DateUtils.parse(criteria.getFromDate());
        Date toDate = DateUtils.parse(criteria.getToDate());
        if (Objects.nonNull(fromDate)) {
            ok &= !fromDate.after(appointment.getStartTime());
        }
        if (Objects.nonNull(toDate)) {
            ok &= !appointment.getStartTime().after(toDate);
        }
        return ok;
    }
}
