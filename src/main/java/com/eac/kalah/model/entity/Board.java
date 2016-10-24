package com.eac.kalah.model.entity;

import java.util.Date;
import java.util.UUID;

import com.eac.kalah.model.entity.enums.PlayerEnum;
import com.eac.kalah.model.entity.enums.WinnerEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by eduardo on 19/10/16.
 */
@EqualsAndHashCode(of = {"id"})
public class Board {

    @Getter
    private String id;
    @Getter
    private Player playerOne;
    @Getter
    private Player playerTwo;
    @Setter
    @Getter
    private PlayerEnum currentPlayer;
    @Setter
    @Getter
    private WinnerEnum winner;
    @Getter
    private Date createdDate;

    @JsonCreator
    public Board(@JsonProperty("playerOne") Player playerOne, @JsonProperty("playerTwo") Player playerTwo) {
        this.id = UUID.randomUUID().toString();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.createdDate = new Date();
        this.currentPlayer = PlayerEnum.ONE;
    }
}