package com.eac.kalah.model.entity;

import com.eac.kalah.model.entity.enums.PlayerEnum;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eduardo on 19/10/16.
 */
@Data
public class Player {

    private PlayerEnum id;
    private List<Pit> pits = new LinkedList<>();
    private House house;

    public Player(PlayerEnum id, List<Pit> pits) {
        this.id = id;
        this.pits = pits;
        this.house = new House();
    }
}