package com.api.parkingcontrol.services;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class ParkingSpotService {

    @Autowired
    public ParkingSpotRepository parkingSpotRepository;

    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {//Salvar nova vaga de estacionamento
        return parkingSpotRepository.save(parkingSpotModel);
    }

    //verifica se já existe a placa cadastrada
    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    //verifica se já existe a numero da vaga de estacionamento cadastrada
    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }
    //verifica se já existe apartamento e bloco cadastrados
    public boolean existsByApartmentAndBlock(String apartment, String block){
        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
    }

    public List<ParkingSpotModel> findAll() {
        return parkingSpotRepository.findAll();
    }
    public Page<ParkingSpotModel> findAll(Pageable pageable) {
        return parkingSpotRepository.findAll(pageable);
    }

    public ResponseEntity<Object> findById(UUID id) {
        var result = parkingSpotRepository.findById(id);
        if(!result.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot Not Found!");
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(result.get());
        }
    }

    @Transactional
    public ResponseEntity<Object> deleteById(UUID id) {
        var result = parkingSpotRepository.findById(id);
        if(!result.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot Not Found!");
        }else{
            parkingSpotRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("successfully deleted parking spot");
        }
    }

    public ResponseEntity<Object> updateById(UUID id, ParkingSpotDto parkingSpotDto) {

        var result = parkingSpotRepository.findById(id);
        if(!result.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot Not Found");
        }else{
            var parkingSpotModel = new ParkingSpotModel();
            BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
            parkingSpotModel.setId(result.get().getId());
            parkingSpotModel.setRegistrationDate(result.get().getRegistrationDate());
            return ResponseEntity.status(HttpStatus.CREATED).body(this.save(parkingSpotModel));
        }
    }
}
