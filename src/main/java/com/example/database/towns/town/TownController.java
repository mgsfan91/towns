package com.example.database.towns.town;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/towns")
public class TownController {

    @Autowired
    TownService townService;

    @GetMapping("/byCreated")
    public List<Town> getAllByCreated() {
        return townService.getTowns();
    }

    @GetMapping("/byPopularity")
    public List<Town> getAllByPopularity() {
        return townService.getTownsByPopularity();
    }
}
