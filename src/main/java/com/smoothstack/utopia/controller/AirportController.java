package com.smoothstack.utopia.controller;

import com.smoothstack.utopia.exception.*;
import com.smoothstack.utopia.entity.Airport;
import com.smoothstack.utopia.service.AirportService;

import java.util.List;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService service;

    @PostMapping
    public ResponseEntity<Airport> create(@Valid @RequestBody final Airport airport) {
        service.save(airport);
        return ResponseEntity.ok(airport);
    }

    @GetMapping
    public @ResponseBody List<Airport> readAll() {
        return service.selectAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Airport> readByCode(@PathVariable final String code) {
        final Airport airport = service.selectByCode(code).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(airport);
    }

    @PutMapping("/{code}")
    public ResponseEntity<String> updateByCode(@PathVariable final String code, @Valid @RequestBody final Airport airport) {
        if(code != airport.getCode()) {
            throw new InvalidUpdateIdException();
        }
        final Airport _ogAirport = service.selectByCode(code).orElseThrow(NotFoundException::new);
        service.save(airport);
        return ResponseEntity.ok("Airport updated successfully");
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteByCode(@Valid @PathVariable final String code) {
        final Airport airport = service.selectByCode(code).orElseThrow(NotFoundException::new);
        service.delete(airport);
        return ResponseEntity.noContent().build();
    }
}
