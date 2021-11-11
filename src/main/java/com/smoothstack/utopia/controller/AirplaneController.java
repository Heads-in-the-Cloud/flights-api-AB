package com.smoothstack.utopia.controller;

import com.smoothstack.utopia.exception.*;
import com.smoothstack.utopia.entity.Airplane;
import com.smoothstack.utopia.service.AirplaneService;

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
@RequestMapping("/api/airplanes")
public class AirplaneController {

    private final AirplaneService service;

    @PostMapping
    public ResponseEntity<Airplane> create(@Valid @RequestBody final Airplane airplane) {
        service.save(airplane);
        return ResponseEntity.ok(airplane);
    }

    @GetMapping
    public @ResponseBody List<Airplane> readAll() {
        return service.selectAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airplane> readById(@PathVariable final Integer id) {
        final Airplane airplane = service.selectById(id).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(airplane);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateById(@PathVariable final Integer id, @Valid @RequestBody final Airplane airplane) {
        if(id != airplane.getId()) {
            throw new InvalidUpdateIdException();
        }
        final Airplane _ogAirplane = service.selectById(id).orElseThrow(NotFoundException::new);
        service.save(airplane);
        return ResponseEntity.ok("Airplane updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable final Integer id) {
        final Airplane airplane = service.selectById(id).orElseThrow(NotFoundException::new);
        service.delete(airplane);
        return ResponseEntity.noContent().build();
    }
}
