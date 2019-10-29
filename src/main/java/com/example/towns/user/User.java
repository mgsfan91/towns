package com.example.towns.user;

import com.example.towns.exception.TownNotFoundException;
import com.example.towns.town.Town;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
public class User {

    @Id
    @Column
    private String username;

    @Column
    @NotNull
    private String password;

    @ManyToMany
    @JoinTable(
            name="town_user",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="town_id")
    )
    private List<Town> towns;

    public int addTowns(Town town) {
        if (towns == null) {
            towns = new ArrayList<>();
        }
        if (towns.contains(town)) {
            throw new RuntimeException("Town is already in the user: " + town);
        }
        towns.add(town);
        return towns.size();
    }

    public int removeTown(Town town) {
        if (towns == null) {
            towns = new ArrayList<>();
        }
        if (! towns.contains(town)) {
            throw new TownNotFoundException("User does not have the town: " + town);
        }
        towns.remove(town);
        return towns.size();
    }

}
