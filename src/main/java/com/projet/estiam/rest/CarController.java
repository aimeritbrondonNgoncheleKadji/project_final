package com.projet.estiam.rest;

import com.projet.estiam.model.Car;
import com.projet.estiam.model.Passenger;
import com.projet.estiam.service.CarService;
import com.projet.estiam.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private PassengerService passengerService;

    @PostMapping
    public ResponseEntity<Car> createCarWithPassengers(@RequestBody Car car) throws IOException {

        Car savedCar = carService.save(car);

        for (Passenger passenger : car.getPassengers()) {
            passenger.setCar(savedCar);
            passengerService.savePassenger(passenger);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
    }


    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long carId) {
        Car car = carService.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException(carId.toString()));
        return ResponseEntity.ok().body(car);
    }

    @GetMapping("/cars")
    public ResponseEntity<Page<Car>> getCars(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Car> cars = carService.findAll(paging);
        if (cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(cars);
        }
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable(value = "id") Long carId,
                                         @RequestBody Car carDetails) throws IOException {
        Car car = carService.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException(carId.toString()));
        car.setModel(carDetails.getModel());
        car.setMake(carDetails.getMake());
        car.setImage(carDetails.getImage());
        car.setPassengers(carDetails.getPassengers());
        Car updatedCar = carService.save(car);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/cars/{id}")
    public Map<String, Boolean> deleteCar(@PathVariable(value = "id") Long carId) {
        Car car = carService.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException(carId.toString()));
        carService.delete(carId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


    /*exercice springBoot gradle: 1 ) vous allez créer une entité Passenger ,
    en relation one to many avec la class Car. Car a un attribut image qui correspond a l'image des de la voiture de ce fait on pourra uploader et donloader des images
    2 ) vous allez créer
 un algorithme qui vous permet de créer au démarrage de votre appliquation,5voitures(avec image) et pour chaque voiture entre 20 et 30 passageres.
    3- dans la couche (web) controller vous allez créer une methode post qui vous permets de creer une voiture avec une liste de passageres et des contollers pour lister des voitures et des passagers.
    4- les users pourront creer des comptes et se connecter . on pourra paginer*/
}