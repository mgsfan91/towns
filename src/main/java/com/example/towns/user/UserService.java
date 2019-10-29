package com.example.towns.user;

import com.example.towns.town.Town;
import com.example.towns.town.TownRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TownRepository townRepository;


    public int selectTown(String username, int townId) {
        User user = userRepository.findById(username)
                .orElseThrow( () -> new RuntimeException("username not found: " + username));
        Town town = townRepository.findById(townId)
                .orElseThrow( () -> new RuntimeException(" town not found: " + townId));

        user.addTowns(town);
        town.incrementPopularity();
        return town.getId();
    }

    public int deselectTown(String username, int townId) {
        User user = userRepository.findById(username)
                .orElseThrow( () -> new RuntimeException("username not found: " + username));
        Town town = townRepository.findById(townId)
                .orElseThrow( () -> new RuntimeException(" town not found: " + townId));

        user.removeTown(town);
        town.decrementPopularity();
        return town.getId();
    }

    public List<Town> getFavouriteTowns(String username) {
        User user = userRepository.findById(username)
                .orElseThrow( () -> new RuntimeException("username not found: " + username));

        return user.getTowns();
    }
}
