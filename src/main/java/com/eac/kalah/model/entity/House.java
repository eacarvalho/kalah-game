package com.eac.kalah.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by eduardo on 19/10/16.
 */
public class House {

    @Getter
    @Setter
    private int stones;

    @JsonCreator
    public House(@JsonProperty("stones") int stones) {
        this.stones = stones;
    }
}