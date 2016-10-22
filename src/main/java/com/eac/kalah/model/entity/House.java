package com.eac.kalah.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by eduardo on 19/10/16.
 */
public class House {

    @Getter
    @Setter
    private int stones;

    public House(@JsonProperty("stones") int stones) {
        this.stones = stones;
    }
}