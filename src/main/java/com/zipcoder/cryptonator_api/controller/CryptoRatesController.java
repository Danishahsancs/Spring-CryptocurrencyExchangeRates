package com.zipcoder.cryptonator_api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zipcoder.cryptonator_api.services.CryptoRatesService;
import com.zipcoder.cryptonator_api.domain.CryptoRates;

@RestController
@RequestMapping("crypto-rate/api")
@CrossOrigin("*")
public class CryptoRatesController {

    // @Autowired
    // private CryptoRatesRepository repo;

    @Autowired
    private CryptoRatesService service;

    // @GetMapping
    // public List<CryptoRates> getAllCryptoRates() {
    // return repo.findAll();
    // }

    @GetMapping
    public List<CryptoRates> getAllCryptoRates() {
        return service.getAllCryptoRates();
    }

    // @GetMapping("/{name}")
    // public ResponseEntity<CryptoRates> findCryptoRatesByName(@PathVariable String
    // name) {
    // return repo.findByName(name)
    // .map(ResponseEntity::ok)
    // .orElse(ResponseEntity.notFound().build());
    // }

    @GetMapping("/{name}")
    public ResponseEntity<CryptoRates> findCryptoRatesByName(@PathVariable String name) {
        return service.findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // @PostMapping
    // public ResponseEntity<?> createCryptoRates(@Valid @RequestBody CryptoRates
    // crypto) {
    // try {
    // CryptoRates savedCryptoRates = repo.save(crypto);
    // return ResponseEntity.status(HttpStatus.CREATED).body(savedCryptoRates);
    // } catch (Exception e) {
    // return ResponseEntity.badRequest().body("Error: Could not create Crypto Rate-
    // " + e.getMessage());
    // }
    // }

    @PostMapping
    public ResponseEntity<?> createCryptoRates(@Valid @RequestBody CryptoRates crypto) {
        if (service.findByName(crypto.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Crypto with name " + crypto.getName() + " already exists.");
        }
        CryptoRates saved = service.saveCryptoRates(crypto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // @PutMapping("/{name}")
    // public ResponseEntity<?> updateCryptoRates(@PathVariable String name, @Valid
    // @RequestBody CryptoRates crypto) {
    // try {
    // Optional<CryptoRates> optionalCrpyto = repo.findByName(name);

    // if (!optionalCrpyto.isPresent()) {
    // return ResponseEntity.notFound().build();
    // }

    // CryptoRates updatedCryptoRates = optionalCrpyto.get();
    // updatedCryptoRates.setPrice(crypto.getPrice());
    // updatedCryptoRates.setDayChange(crypto.getDayChange());
    // updatedCryptoRates.setDayVolume(crypto.getDayVolume());
    // updatedCryptoRates.setLastCheckedTime(crypto.getLastCheckedTime());

    // return ResponseEntity.ok(repo.save(updatedCryptoRates));

    // } catch (Exception e) {
    // return ResponseEntity.badRequest().body("could not update " + name + " " +
    // e.getMessage());
    // }
    // }

    @PutMapping("/{name}")
    public ResponseEntity<?> updateCryptoRates(@PathVariable String name, @Valid @RequestBody CryptoRates crypto) {
        return service.update(name, crypto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // @DeleteMapping("/{name}")
    // public ResponseEntity<?> deleteCryptoRates(@PathVariable String name) {
    // try {
    // Optional<CryptoRates> optionalCryptoRates = repo.findByName(name);

    // if (!optionalCryptoRates.isPresent()) {
    // return ResponseEntity.notFound().build();
    // }
    // repo.delete(optionalCryptoRates.get());
    // return ResponseEntity.ok().body(name+" deleted successfully");

    // } catch (Exception e) {
    // return ResponseEntity.badRequest().body("could not delete " + name + " " +
    // e.getMessage());
    // }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteCryptoRates(@PathVariable String name) {
        return service.delete(name)
                ? ResponseEntity.ok(name + " deleted successfully")
                : ResponseEntity.notFound().build();
    }
}
