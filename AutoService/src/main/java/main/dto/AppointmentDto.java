package main.dto;

import lombok.Getter;
import lombok.Setter;
import main.entity.Appointment;

import java.util.Date;

@Getter
@Setter
public class AppointmentDto extends Appointment {
    private Integer duration;
    private String startTimeStr;
    private String managerName;
    private String userFio;
    private String userPhone;
    private Integer cost;
    private String productName;
}
