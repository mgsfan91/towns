package com.example.towns.town;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "towns")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    private Integer id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String description;

    @Column
    @NotNull
    private Integer population;

    @Column
    private Integer popularityCnt;

    public int incrementPopularity() {
        popularityCnt++;
        return popularityCnt;
    }

    public int decrementPopularity() {
        if (popularityCnt <= 0) {
            throw new RuntimeException("Cant decrement popularityCnt, it was: " + popularityCnt);
        }
        popularityCnt--;
        return popularityCnt;
    }

    @Override
    public String toString() {
        return "Town ID: " + id + ", name: " + name + ", population: " + population;
    }
}
