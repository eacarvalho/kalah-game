package com.eac.kalah.model.entity;

import com.eac.kalah.model.entity.enums.PitEnum;
import com.eac.kalah.model.entity.enums.PlayerEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
    @Setter
    @Getter
    private House house;

    public Player(PlayerEnum id) {
        this.id = id;
        this.pits = this.initializerPits();
        this.house = new House();
    }

    private List<Pit> initializerPits() {
        List<Pit> pits = new ArrayList<>();

        for (PitEnum pitEnum : PitEnum.values()) {
            pits.add(new Pit(pitEnum));
        }

        return pits;
    }
}