package com.example.database.towns.user;

import com.example.database.towns.town.Town;
import com.example.database.towns.town.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TownService townService;


    @PostMapping("town")
    public int addToTowns(@RequestBody Town town, Principal principal) {
        return townService.addTowns(town);
    }

    @GetMapping("towns")
    public List<Town> getFavouriteTowns(Principal principal) {
        return userService.getFavouriteTowns(principal.getName());
    }

    @PostMapping("towns/{townId}")
    public int addTown(@PathVariable("townId") int townId, Principal principal) {
        return userService.selectTown(principal.getName(), townId);
    }

    @DeleteMapping("towns/{townId}")
    public int deleteTown(@PathVariable("townId") int townId, Principal principal) {
        return userService.deselectTown(principal.getName(), townId);
    }
}
