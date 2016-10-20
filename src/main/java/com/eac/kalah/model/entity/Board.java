package com.eac.kalah.model.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import com.eac.kalah.model.entity.enums.PlayerEnum;
import lombok.Data;
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
    @Setter
    @Getter
    private Player playerOne;
    @Setter
    @Getter
    private Player playerTwo;
    @Setter
    @Getter
    private PlayerEnum currentPlayer;
    @Setter
    @Getter
    private PlayerEnum winner;
    @Getter
    private LocalDateTime createdDate;

    public Board() {
        this.id = UUID.randomUUID().toString();
        this.createdDate = LocalDateTime.now();
        this.currentPlayer = PlayerEnum.ONE;
    }
}