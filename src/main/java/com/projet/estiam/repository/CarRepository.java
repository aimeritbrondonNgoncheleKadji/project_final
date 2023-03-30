package com.projet.estiam.repository;

import com.projet.estiam.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car findByMakeAndModel(String make, String model);
}
