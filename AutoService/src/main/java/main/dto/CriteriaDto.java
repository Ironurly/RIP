package main.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CriteriaDto {
    Long managerId;
    String clientFio;
    String clientPhone;
    String fromDate;
    String toDate;
}
