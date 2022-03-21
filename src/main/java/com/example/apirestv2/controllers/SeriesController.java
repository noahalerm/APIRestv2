package com.example.apirestv2.controllers;

import com.example.apirestv2.model.entities.Series;
import com.example.apirestv2.model.services.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SeriesController {
    //ATTRIBUTES
    private final SeriesService seriesService;

    //METHODS

    //GET
    @GetMapping("/series")
    public ResponseEntity<?> getSeries() {
        //LIST
        List<Series> res = seriesService.listSeries();

        //OUTPUT
        if (res != null)
            return ResponseEntity.ok(res);
        else
            return ResponseEntity.notFound().build();
    }

    //GET
    @GetMapping("/series/{id}")
    public ResponseEntity<?> getSeriesById(@PathVariable long id) {
        //SERIES
        Series series = seriesService.consultById(id);

        //OUTPUT
        if (series != null)
            return ResponseEntity.ok(series);
        else
            return ResponseEntity.notFound().build();
    }

    //POST
    @PostMapping("/series")
    public ResponseEntity<?> addSeries(@RequestBody Series series) {
        try {
            //INSERT
            seriesService.addSeries(series);

            //OUTPUT
            return new ResponseEntity<>(series, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    //PUT
    @PutMapping("/series")
    public ResponseEntity<?> modifySeries(@RequestBody Series modSeries){
        //UPDATE
        Series res = seriesService.modifySeries(modSeries);

        //OUTPUT
        if (res != null)
            return ResponseEntity.ok(res);
        else
            return ResponseEntity.notFound().build();
    }

    //DELETE
    @DeleteMapping("/series/{id}")
    public ResponseEntity<?> deleteSeries(@PathVariable long id){
        //DELETE
        Series res = seriesService.deleteSeries(id);

        //OUTPUT
        if (res != null)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }
}
