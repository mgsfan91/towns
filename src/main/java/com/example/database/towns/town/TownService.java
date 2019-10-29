package com.example.database.towns.town;

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

    public int addTowns(Town town) {
        town.setId(0);
        town.setPopularityCnt(0);
        Town saved = townRepository.save(town);
        return saved.getId();
    }

    public List<Town> getTownsByPopularity() {
        return townRepository.findByOrderByPopularityCntDesc();
    }
}
