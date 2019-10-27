package com.example.towns.user;

import com.example.towns.town.Town;
import com.example.towns.town.TownService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public List<User> all( Principal principal) {
        log.info("____ all uso " + principal.getName());
        return userService.getUsers();
    }

    @PostMapping("town")
    public int addToTowns(@RequestBody Town town, Principal principal) {
        log.info("____ addToTowns uso " + principal);
        return townService.addTowns(principal.getName(), town);
    }

    @PostMapping("towns/{townId}")
    public int addTown(@PathVariable("townId") int townId, Principal principal) {
        log.info("____ addTown uso " + principal);
        return userService.selectTown(principal.getName(), townId);
    }

    @DeleteMapping("towns/{townId}")
    public int deleteTown(@PathVariable("townId") int townId, Principal principal) {
        return userService.deselectTown(principal.getName(), townId);
    }
}
