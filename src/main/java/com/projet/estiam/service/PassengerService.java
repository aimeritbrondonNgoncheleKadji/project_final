package com.projet.estiam.service;

import com.projet.estiam.model.Passenger;
import com.projet.estiam.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public Passenger savePassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }
}
