package com.example.testcarmanagement.global;

import com.example.testcarmanagement.entity.Car;
import lombok.Data;

@Data
public class ResponseListCar extends Car {
    private Double distance;
}
