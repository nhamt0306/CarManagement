package com.example.testcarmanagement.controller;


import com.example.testcarmanagement.entity.Car;
import com.example.testcarmanagement.global.CarMapper;
import com.example.testcarmanagement.global.NearCar;
import com.example.testcarmanagement.global.ResponseCar;
import com.example.testcarmanagement.global.ResponseObject;
import com.example.testcarmanagement.inputData.ListCarNearCoordinate;
import com.example.testcarmanagement.service.CarServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/cars")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class CarController {
    private final CarServiceImpl carService;
    private final CarMapper carMapper;
//    API cho phép thêm vào xe ô tô mới, tự tạo id
    @PostMapping(value = "/create")
    public ResponseEntity<ResponseObject> createNewCar(@RequestBody Car newCar){
        // check car is existed in database
        List<Car> foundCarInDatabase = carService.findCarByLicensePlate(newCar.getLicensePlate().trim());
        if(foundCarInDatabase.size() >0){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "This car is existed in database", "")
            );
        }
        // add new car
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Add new car successfully", carService.saveCar(newCar))
        );
    }

//    API cho phép xóa xe ô tô bằng id
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<ResponseObject> deleteCar(@PathVariable long id){
        // check car existed
        Car foundCar = carService.findCarById(id);
        if (Objects.isNull(foundCar)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Can not find this car with id =" + id, "")
            );
        }
        // delete
        carService.deleteCar(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Delete car successfully", "")
        );
    }


//    API cho phép thay đổi thông tin ô tô bằng id
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ResponseObject> updateCar(@PathVariable long id, @RequestBody Car infoCar){
        // check car existed
        Car foundCar = carService.findCarById(id);
        if (Objects.isNull(foundCar)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Can not find this car with id =" + id, "")
            );
        }
        // update
        if(infoCar.getLicensePlate() != null){
            // check car is existed in database
            if(carService.findCarByLicensePlate(infoCar.getLicensePlate().trim()).size() >0){
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseObject("failed", "This car is existed in database", "")
                );
            }
            foundCar.setLicensePlate(infoCar.getLicensePlate());
        }
        if(infoCar.getOwnerName() != null){
            foundCar.setOwnerName(infoCar.getOwnerName());
        }
        if(infoCar.getOwnerBirthday() != null){
            foundCar.setOwnerBirthday(infoCar.getOwnerBirthday());
        }
        if(infoCar.getX() != null){
            foundCar.setX(infoCar.getX());
        }
        if(infoCar.getY() != null){
            foundCar.setY(infoCar.getY());
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update car successfully", carService.saveCar(foundCar))
        );
    }
//    API cho phép nhập vào tọa độ x:y, và số lượng xe cần tìm n. Tìm n xe gần tọa độ x:y nhất và trả về ra danh sách xe ô tô (id, licensePlate, distance) với thứ tự từ gần đến xa và id tăng dần
    @GetMapping(value = "/get-car-by-coordinate")
    public ResponseEntity<ResponseObject> getListCarByCoordinate(@RequestBody ListCarNearCoordinate input){
        // Lấy ra tất cả các điểm
        List<Car> getAllCar = carService.getAllCar();
        if(getAllCar.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Don't have any car in database", "")
            );
        }
        if(getAllCar.size() < input.getN()){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Don't have enough car to find", "")
            );
        }
        // Tính khoảng cách từ điểm nhập vào đến tất cả các điểm
        // Lưu danh sách các điểm theo id và khoảng cách tương ứng
        List<ResponseCar> responseListCars = new ArrayList<>();
        for(Car car : getAllCar){
            ResponseCar responseCarItem = carMapper.mapperCarToResponse(car);
            Double distance = Math.sqrt(Math.pow(car.getX() - input.getX(),2)+ Math.pow(car.getY() - input.getY(),2));
            // save in list
            responseCarItem.setDistance(distance);
            responseListCars.add(responseCarItem);
        }

        // Sắp xếp khoảng cách theo thứ tự tăng dần
        Collections.sort(responseListCars, new Comparator<ResponseCar>() {
            @Override
            public int compare(ResponseCar o1, ResponseCar o2) {
                if(o1.getDistance() > o2.getDistance()){
                    return 1; // swap index
                }else if (o1.getDistance() < o2.getDistance()){
                    return -1; // don't swap
                }else
                    return 0; // same distance
            }
        });
        List<ResponseCar> answerList = responseListCars.subList(0, input.getN());
        // xuất ra danh sách các điểm
        List<NearCar> nearCars = carMapper.mapperListResponseCarToResponseData(answerList);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Get list car near this coordinate successfully",  nearCars)
        );
    }
}
