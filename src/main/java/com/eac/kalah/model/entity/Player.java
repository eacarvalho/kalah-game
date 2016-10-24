package com.eac.kalah.model.entity;

import com.eac.kalah.model.entity.enums.PitEnum;
import com.eac.kalah.model.entity.enums.PlayerEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by eduardo on 19/10/16.
 */
public class Player {

    @Getter
    private PlayerEnum id;
    @Getter
    private List<Pit> pits = new LinkedList<>();
    @Getter
    private House house;

    @JsonCreator
    public Player(@JsonProperty("id") PlayerEnum id) {
        this.id = id;
        this.pits = this.initializerPits();
        this.house = new House(0);
    }

    private List<Pit> initializerPits() {
        List<Pit> pits = new ArrayList<>();

        for (PitEnum pitEnum : PitEnum.values()) {
            pits.add(new Pit(pitEnum));
        }

        return pits;
    }
}