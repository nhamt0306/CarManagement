package com.example.testcarmanagement.repository;

import com.example.testcarmanagement.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByLicensePlate(String licensePlate);
}
