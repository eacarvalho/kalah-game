package com.eac.kalah.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eac.kalah.model.entity.Board;
import com.eac.kalah.model.entity.House;
import com.eac.kalah.model.entity.Pit;
import com.eac.kalah.model.entity.Player;
import com.eac.kalah.model.entity.enums.PitEnum;
import com.eac.kalah.model.entity.enums.PlayerEnum;
import com.eac.kalah.model.repository.BoardRepository;
import com.eac.kalah.service.BoardService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by eduardo on 19/10/16.
 */
@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository repository;

    @Override
    public Board create() {
        Board board = new Board();
        board.setPlayerOne(new Player(PlayerEnum.ONE));
        board.setPlayerTwo(new Player(PlayerEnum.TWO));
        return repository.save(board);
    }

    @Override
    public Board move(String boardId, PitEnum pitId) {
        Board board = findById(boardId);
        Player currentPlayer = getCurrentPlayer(board);
        List<Pit> pits = currentPlayer.getPits();
        Pit currentPit = pits.get(pitId.getPosition());
        int stones = currentPit.getStones();
        int position = currentPit.getId().getPosition() + 1;

        currentPit.setStones(0);

        while (stones > 0) {
            stones = moveStones(position, stones, board, currentPlayer.getId());
            position = 0;
        }

        log.info("Player One: " + board);

        return board;
    }

    @Override
    public Board findById(String id) {
        return repository.findById(id);
    }

    private int moveStones(int position, int stones, Board board, PlayerEnum currentPlayer) {
        Player player = this.getCurrentPlayer(board);

        board.setCurrentPlayer(this.getOpponentPlayer(currentPlayer));

        for (int i = position; i < 6; i++) {
            if (stones > 0) {
                Pit pit = player.getPits().get(i);
                pit.setStones(pit.getStones() + 1);

                // TODO Colocar a regra das pedras quando a stone estiver zero

                stones--;
            } else {
                return stones;
            }
        }

        if (stones > 0) {
            House house = player.getHouse();
            house.setStones(house.getStones() + 1);
            stones--;

            if (stones == 0 && player.getId() == currentPlayer) {
                board.setCurrentPlayer(currentPlayer);
            }
        }

        return stones;
    }

    private Player getCurrentPlayer(final Board board) {
        return board.getCurrentPlayer() == PlayerEnum.ONE ? board.getPlayerOne() : board.getPlayerTwo();
    }

    private PlayerEnum getOpponentPlayer(final PlayerEnum currentPlayer) {
        return currentPlayer == PlayerEnum.ONE ? PlayerEnum.TWO : PlayerEnum.ONE;
    }
}