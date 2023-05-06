package com.github.Spring_Boot_Zadatak_izdavanje_kartica.controller;

import com.github.Spring_Boot_Zadatak_izdavanje_kartica.model.Osoba;
import com.github.Spring_Boot_Zadatak_izdavanje_kartica.repo.OsobaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class OsobaController {

    @Autowired
    private OsobaRepo osobaRepo;

    @GetMapping("/getAllOsobe")
    public ResponseEntity<List<Osoba>> getAllOsobe() {
        try {
            List<Osoba> osobaList = new ArrayList<>();
            osobaRepo.findAll().forEach(osobaList::add);

            if(osobaList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(osobaList, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getOsobaById/{id}")
    public ResponseEntity<Osoba> getOsobaById(@PathVariable Long id) {
        Optional<Osoba> osobaData = osobaRepo.findById(id);

        if(osobaData.isPresent()) {
            return new ResponseEntity<>(osobaData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    @PostMapping("/addOsoba")
    public ResponseEntity<Osoba> addOsoba(@RequestBody Osoba osoba) {
        Osoba osobaObj = osobaRepo.save(osoba);

        return new ResponseEntity<>(osobaObj, HttpStatus.OK);

    }

    @PostMapping
    public void updateOsobaById() {

    }

    @DeleteMapping
    public void deleteOsobaById() {

    }

}
