package com.github.Spring_Boot_Zadatak_izdavanje_kartica.controller;

import com.github.Spring_Boot_Zadatak_izdavanje_kartica.model.Osoba;
import com.github.Spring_Boot_Zadatak_izdavanje_kartica.model.OsobaWithoutId;
import com.github.Spring_Boot_Zadatak_izdavanje_kartica.repo.OsobaRepo;
import com.opencsv.CSVWriter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.Long.parseLong;

@RestController
public class OsobaController {

    @Autowired
    private OsobaRepo osobaRepo;

    @PostMapping(value = "/addOsoba")
    public ResponseEntity<Osoba> addOsoba(@RequestBody Resource osobaResource) throws IOException {
        String osobaString = osobaResource.getContentAsString(StandardCharsets.UTF_8);

        List<String> osobaList = Arrays.asList(osobaString.split("\\s*,\\s*"));

        Osoba osobaObj = new Osoba();
        osobaObj.setIme(osobaList.get(0));
        osobaObj.setPrezime(osobaList.get(1));
        osobaObj.setOIB(parseLong(osobaList.get(2)));
        osobaObj.setStatus(osobaList.get(3));

        osobaRepo.save(osobaObj);

        return new ResponseEntity<>(osobaObj, HttpStatus.OK);
    }

    @GetMapping("/getOsobaByOIB")
    public ResponseEntity<OsobaWithoutId> getOsobaByOIB(@RequestParam(name = "OIB") Long OIB) throws IOException {
        Optional<Osoba> osobaData = osobaRepo.findByOIB(OIB);

        if(osobaData.isPresent()) {
            OsobaWithoutId osobaWithoutId = new OsobaWithoutId(osobaData.get().getIme(), osobaData.get().getPrezime(),
                    osobaData.get().getOIB(), osobaData.get().getStatus());

            String str = "2023-05-07 15:00:00";
            Timestamp timestamp = Timestamp.valueOf(str);
            Long timestampMili = timestamp.getTime();
            String filePathString = "C:/Zadatak_izdavanje_kartica_pomocni_resursi/csv_fajlovi/" +
                    osobaWithoutId.getOIB() + timestampMili + ".csv";
            Path path = Paths.get(filePathString);

            if (!Files.exists(path)) {
                try {

                    //napravi folder ako ga nema
                    Files.createDirectories(Paths.get("C:/Zadatak_izdavanje_kartica_pomocni_resursi/csv_fajlovi"));

                    File osobaFile = new File(filePathString);
                    FileWriter outputFile = new FileWriter(osobaFile);

                    CSVWriter writer = new CSVWriter(outputFile,
                            CSVWriter.DEFAULT_SEPARATOR,
                            CSVWriter.NO_QUOTE_CHARACTER,
                            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                            CSVWriter.RFC4180_LINE_END);

                    String[] stringArrayToWrite = {osobaWithoutId.getIme(), osobaWithoutId.getPrezime(),
                            String.valueOf(osobaWithoutId.getOIB()), osobaWithoutId.getStatus()};

                    writer.writeNext(stringArrayToWrite);
                    writer.close();

                    return new ResponseEntity<>(osobaWithoutId, HttpStatus.CREATED);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else {
                return new ResponseEntity<>(osobaWithoutId, HttpStatus.OK);
            }

        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteOsobaByOIB")
    @Transactional
    public ResponseEntity<HttpStatus> deleteOsobaByOIB(@RequestParam(name = "OIB") Long OIB) throws IOException {
        osobaRepo.deleteByOIB(OIB);

        return new ResponseEntity<>(HttpStatus.OK);
    }

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

    @DeleteMapping("/deleteOsobaById/{id}")
    public ResponseEntity<HttpStatus> deleteOsobaById(@PathVariable Long id) {
        osobaRepo.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
