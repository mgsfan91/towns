package com.example.database.towns.town;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TownRepository extends JpaRepository<Town, Integer> {
    List<Town> findByOrderByPopularityCntDesc();
}
