package com.example.testcarmanagement.service;


import com.example.testcarmanagement.entity.Car;
import com.example.testcarmanagement.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepository carRepository;


    @Override
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public Car findCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public List<Car> findNCarByCoordinate(Integer n, Integer x, Integer y) {
        return null;
    }

    @Override
    public List<Car> findCarByLicensePlate(String lp) {
        return carRepository.findByLicensePlate(lp);
    }

    @Override
    public List<Car> getAllCar() {
        return carRepository.findAll();
    }
}
