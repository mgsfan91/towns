package com.example.database.towns.user;

import com.example.database.towns.setup.TownNotFoundException;
import com.example.database.towns.town.Town;
import com.example.database.towns.town.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TownRepository townRepository;


    public int selectTown(String username, int townId) {
        User user = userRepository.findById(username).get();
        Town town = townRepository.findById(townId)
                .orElseThrow( () -> new TownNotFoundException("Not found: " + townId));

        user.addTowns(town);
        town.incrementPopularity();
        return town.getId();
    }

    public int deselectTown(String username, int townId) {
        User user = userRepository.findById(username).get();
        Town town = townRepository.findById(townId)
                .orElseThrow( () -> new TownNotFoundException("Not found: " + townId));

        user.removeTown(town);
        town.decrementPopularity();
        return town.getId();
    }

    public List<Town> getFavouriteTowns(String username) {
        User user = userRepository.findById(username).get();

        return user.getTowns();
    }
}
