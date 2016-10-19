package com.eac.kalah.model.entity;

import com.eac.kalah.model.entity.enums.PitEnum;
import lombok.Data;

/**
 * Created by eduardo on 19/10/16.
 */
@Data
public class Pit {

    private static final int INITIAL_STONES = 6;

    private PitEnum id;
    private int stones;

    public Pit(PitEnum id) {
        this.id = id;
        this.stones = INITIAL_STONES;
    }
}