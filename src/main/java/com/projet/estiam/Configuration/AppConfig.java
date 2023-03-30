package com.projet.estiam.Configuration;

import com.projet.estiam.model.Car;
import com.projet.estiam.model.Passenger;
import com.projet.estiam.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Configuration
public class AppConfig {

    @Autowired
    CarService carService;
    @Bean
    public CommandLineRunner createCars(CarService carService) throws IOException {

        return args -> {
            List<Car> cars = Arrays.asList(
                    new Car("Toyota", "Corolla"),
                    new Car("Honda", "Civic"),
                    new Car("Ford", "Mustang"),
                    new Car("Chevrolet", "Camaro")
            );
            for (Car car : cars) {
                Car existingCar = carService.findByMakeAndModel(car.getMake(), car.getModel());
                if (existingCar == null) {
                    carService.save(car);
                }
            }
        };
    }

    @Bean
    public CommandLineRunner createPassengersForCars(CarService carService) {
        return args -> {
            Random random = new Random();

            List<Car> cars = carService.getAllCars();
            for (Car car : cars) {
                int numberOfPassengers = 20 + random.nextInt(11);
                List<Passenger> passengers = new ArrayList<>();

                for (int i = 0; i < numberOfPassengers; i++) {
                    Passenger passenger = new Passenger();
                    passenger.setName("Passenger " + (i + 1));
                    passenger.setCar(car);
                    passengers.add(passenger);
                }

                car.setPassengers(passengers);
                carService.save(car);
            }
        };
    }
}