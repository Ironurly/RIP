package main.service;

import main.TestUtils;
import main.dto.AppointmentDto;
import main.dto.CriteriaDto;
import main.entity.Appointment;
import main.entity.Manager;
import main.entity.Product;
import main.entity.Session;
import main.repository.AppointmentRepository;
import main.repository.SessionRepository;
import main.utils.DateUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class AppointmentServiceTest extends BaseServiceTest {
    @Test
    public void testFindCurrentUserAppointments() {
        List<AppointmentDto> appointments = appointmentService.findCurrentUserAppointments();

        Assertions.assertEquals(1, appointments.size());
        Assertions.assertEquals(product.getId(), appointments.get(0).getProductId());
    }

    @Test
    public void testMapToAppointmentDto() {
        AppointmentDto appointmentDto = appointmentService.mapToAppointmentDto(appointment);

        // Поля Appointment
        Assertions.assertEquals(appointment.getId(), appointmentDto.getId());
        Assertions.assertEquals(appointment.getUserId(), appointmentDto.getUserId());
        Assertions.assertEquals(appointment.getCarNumber(), appointmentDto.getCarNumber());
        Assertions.assertEquals(appointment.getCarBrand(), appointmentDto.getCarBrand());
        Assertions.assertEquals(appointment.getProductId(), appointmentDto.getProductId());
        Assertions.assertEquals(appointment.getStartTime(), appointmentDto.getStartTime());

        // Новые поля AppointmentDto
        Assertions.assertEquals("2020-01-01T00:00", appointmentDto.getStartTimeStr());
        Assertions.assertEquals("manager-name", appointmentDto.getManagerName());
        Assertions.assertEquals("product-name", appointmentDto.getProductName());
        Assertions.assertEquals("user-name", appointmentDto.getUserFio());
        Assertions.assertEquals("user-phone", appointmentDto.getUserPhone());
    }

    @Test
    public void testCreateDto() {
        // Кейс, когда достаём Appointment из БД
        AppointmentDto appointmentDto = appointmentService.createDto(appointment.getId(), -1L);

        Assertions.assertEquals(appointment.getId(), appointmentDto.getId());
        Assertions.assertEquals(appointment.getUserId(), appointmentDto.getUserId());
        Assertions.assertEquals(appointment.getCarNumber(), appointmentDto.getCarNumber());
        Assertions.assertEquals(appointment.getCarBrand(), appointmentDto.getCarBrand());
        Assertions.assertEquals(appointment.getProductId(), appointmentDto.getProductId());
        Assertions.assertEquals(appointment.getStartTime(), appointmentDto.getStartTime());

        // Кейс, когда просто достаём идентификатор продукта
        appointmentDto = appointmentService.createDto(-1L, product.getId());

        Assertions.assertEquals(product.getId(), appointmentDto.getProductId());
    }

    @Nested
    class MatchesCriteria {
        @Test
        public void testManagerId() {
            CriteriaDto criteria = CriteriaDto.builder().managerId(manager.getId()).build();
            Assertions.assertTrue(appointmentService.matches(appointment, criteria));

            criteria.setManagerId(Long.MAX_VALUE);
            Assertions.assertFalse(appointmentService.matches(appointment, criteria));

            criteria.setManagerId(-1L);
            Assertions.assertTrue(appointmentService.matches(appointment, criteria));

            criteria.setManagerId(null);
            Assertions.assertTrue(appointmentService.matches(appointment, criteria));
        }

        @Test
        public void testClientFio() {
            CriteriaDto criteria = CriteriaDto.builder().clientFio("user-name").build();
            Assertions.assertTrue(appointmentService.matches(appointment, criteria));

            criteria.setClientFio("er-na");
            Assertions.assertTrue(appointmentService.matches(appointment, criteria));

            criteria.setClientFio("other");
            Assertions.assertFalse(appointmentService.matches(appointment, criteria));

            criteria.setClientFio(null);
            Assertions.assertTrue(appointmentService.matches(appointment, criteria));
        }

        @Test
        public void testClientPhone() {
            CriteriaDto criteria = CriteriaDto.builder().clientPhone("user-phone").build();
            Assertions.assertTrue(appointmentService.matches(appointment, criteria));

            criteria.setClientPhone("er-ph");
            Assertions.assertTrue(appointmentService.matches(appointment, criteria));

            criteria.setClientPhone("other");
            Assertions.assertFalse(appointmentService.matches(appointment, criteria));

            criteria.setClientPhone(null);
            Assertions.assertTrue(appointmentService.matches(appointment, criteria));
        }
        
        @Test
        public void testDate() {
            Date early = Date.from(appointment.getStartTime().toInstant().plusSeconds(-1000));
            Date late = Date.from(appointment.getStartTime().toInstant().plusSeconds(+1000));

            CriteriaDto criteria = CriteriaDto.builder()
                    .fromDate(DateUtils.toString(early))
                    .toDate(DateUtils.toString(late))
                    .build();
            Assertions.assertTrue(appointmentService.matches(appointment, criteria));

            criteria = CriteriaDto.builder()
                    .fromDate(DateUtils.toString(early))
                    .toDate(DateUtils.toString(early))
                    .build();
            Assertions.assertFalse(appointmentService.matches(appointment, criteria));

            criteria = CriteriaDto.builder()
                    .fromDate(DateUtils.toString(late))
                    .toDate(DateUtils.toString(late))
                    .build();
            Assertions.assertFalse(appointmentService.matches(appointment, criteria));

            criteria = CriteriaDto.builder()
                    .fromDate(DateUtils.toString(early))
                    .toDate(DateUtils.toString(null))
                    .build();
            Assertions.assertTrue(appointmentService.matches(appointment, criteria));

            criteria = CriteriaDto.builder()
                    .fromDate(DateUtils.toString(null))
                    .toDate(DateUtils.toString(late))
                    .build();
            Assertions.assertTrue(appointmentService.matches(appointment, criteria));

            criteria = CriteriaDto.builder()
                    .fromDate(DateUtils.toString(null))
                    .toDate(DateUtils.toString(null))
                    .build();
            Assertions.assertTrue(appointmentService.matches(appointment, criteria));
        }
    }
}
