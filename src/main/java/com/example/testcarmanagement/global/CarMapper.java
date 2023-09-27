package com.example.testcarmanagement.global;

import com.example.testcarmanagement.entity.Car;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarMapper {
    public ResponseCar mapperCarToResponse(Car car){
        ResponseCar responseCar = new ResponseCar();
        responseCar.setX(car.getX());
        responseCar.setY(car.getY());
        responseCar.setLicensePlate(car.getLicensePlate());
        responseCar.setOwnerName(car.getOwnerName());
        responseCar.setOwnerBirthday(car.getOwnerBirthday());
        responseCar.setId(car.getId());
        return responseCar;
    }

    public List<NearCar> mapperListResponseCarToResponseData(List<ResponseCar> responseCars){
        List<NearCar> nearCars = new ArrayList<>();
        for(ResponseCar car : responseCars){
            NearCar nearCar = new NearCar(car.getId(), car.getLicensePlate(), car.getDistance());
            nearCars.add(nearCar);
        }
        return nearCars;
    }
}
