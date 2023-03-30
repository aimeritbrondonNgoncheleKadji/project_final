package com.projet.estiam.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"make", "model"}))
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String make;
    private String model;
    private String image;



    public Car(String make, String model) {
        this.make = make;
        this.model = model;
    }



    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("car")
    private List<Passenger> passengers;

    // Constructors, getters, and setters
}
