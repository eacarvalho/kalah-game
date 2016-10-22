package com.eac.kalah.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by eduardo on 19/10/16.
 */
public class House {

    @Getter
    @Setter
    private int stones;

    public House(int stones) {
        this.stones = stones;
    }
}