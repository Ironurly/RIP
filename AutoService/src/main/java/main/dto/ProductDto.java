package main.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private String manager;
    private String managerDescription;
    private Long managerId;
    private Integer duration;
    private Integer cost;
}
