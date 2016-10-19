package com.eac.kalah.model.entity;

import com.eac.kalah.model.entity.enums.PlayerEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by eduardo on 19/10/16.
 */
@Data
@EqualsAndHashCode(of = {"id"})
public class Board {

    private String id;
    private Player playerOne;
    private Player playerTwo;
    private PlayerEnum currentPlayer;

    public Board(String id) {
        this.id = id;
    }

    public Board(String id, Player playerOne, Player playerTwo) {
        this.id = id;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }
}