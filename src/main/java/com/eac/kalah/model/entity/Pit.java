package com.eac.kalah.model.entity;

import com.eac.kalah.model.entity.enums.PitEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by eduardo on 19/10/16.
 */
@EqualsAndHashCode(of = {"id"})
public class Pit {

    private static final int INITIAL_STONES = 6;

    @Getter
    private PitEnum id;
    @Setter
    @Getter
    private int stones;

    @JsonCreator
    public Pit(@JsonProperty("id") PitEnum id) {
        this.id = id;
        this.stones = INITIAL_STONES;
    }
}