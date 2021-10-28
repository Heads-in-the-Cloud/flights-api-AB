package com.smoothstack.utopia.controller;

import com.smoothstack.utopia.exception.*;
import com.smoothstack.utopia.entity.Route;
import com.smoothstack.utopia.service.RouteService;

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

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService service;

    public RouteController(final RouteService service) {
      this.service = service;
    }

    @PostMapping
    public ResponseEntity<Route> create(@Valid @RequestBody final Route route) {
        service.save(route);
        return ResponseEntity.ok(route);
    }

    @GetMapping
    public @ResponseBody List<Route> readAll() {
        return service.selectAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> readById(@PathVariable final Integer id) {
        final Route route = service.selectById(id).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(route);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateById(@PathVariable final Integer id, @Valid @RequestBody final Route route) {
        if(id != route.getId()) {
            throw new InvalidUpdateIdException();
        }
        final Route _ogRoute = service.selectById(id).orElseThrow(NotFoundException::new);
        service.save(route);
        return ResponseEntity.ok("Route updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable final Integer id) {
        final Route route = service.selectById(id).orElseThrow(NotFoundException::new);
        service.delete(route);
        return ResponseEntity.noContent().build();
    }
}
