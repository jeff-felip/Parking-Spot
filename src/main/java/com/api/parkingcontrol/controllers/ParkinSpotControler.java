package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.models.ParkingSpotModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/parking-spot")
public class ParkinSpotControler {

    @Autowired
    ParkingSpotModel parkingSpotModel;


}
