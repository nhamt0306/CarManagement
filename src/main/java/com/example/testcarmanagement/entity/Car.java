package com.example.testcarmanagement.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cars")
@Getter
@Setter
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String licensePlate;
    private String ownerName;
    private Date ownerBirthday;
    private Integer x;
    private Integer y;
}
