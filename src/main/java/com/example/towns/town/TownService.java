package com.example.towns.town;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TownService {

    @Autowired
    TownRepository townRepository;

    public List<Town> getTowns() {
        return townRepository.findAll();
    }

    public int addTowns(String username, Town town) {
        town.setId(0);
        town.setPopularityCnt(0);
        town.setAddedBy(username);
        Town saved = townRepository.save(town);
        return saved.getId();
    }

    public List<Town> getTownsByPopularity() {
        return townRepository.findByOrderByPopularityCntDesc();
    }
}
