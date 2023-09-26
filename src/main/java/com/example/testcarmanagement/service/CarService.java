package com.example.testcarmanagement.service;


import com.example.testcarmanagement.entity.Car;

import java.util.List;

public interface CarService {
    Car saveCar(Car car);
    void deleteCar(Long id);
    Car findCarById(Long id);
    List<Car> findNCarByCoordinate(Integer n, Integer x, Integer y);
    List<Car> findCarByLicensePlate(String lp);
    List<Car> getAllCar();
}
