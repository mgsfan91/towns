package com.example.towns.user;

import com.example.towns.town.Town;
import com.example.towns.town.TownService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TownService townService;

    @GetMapping
    public List<User> all() {
        return userService.getUsers();
    }

    @PostMapping("town")
    public int addToTowns(@RequestBody Town town) {
        String username = "test";
        return townService.addTowns(username, town);
    }

    @PostMapping("towns/{townId}")
    public int addTown(@PathVariable("townId") int townId) {
        String username = "test";
        return userService.selectTown(username, townId);
    }

    @DeleteMapping("towns/{townId}")
    public int deleteTown(@PathVariable("townId") int townId) {
        String username = "test";
        return userService.deselectTown(username, townId);
    }
}
