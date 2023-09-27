package com.example.testcarmanagement.global;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class NearCar {
    private Long id;
    private String licensePlate;
    private Double distance;

    public NearCar(Long id, String licensePlate, Double distance) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.distance = distance;
    }
}
