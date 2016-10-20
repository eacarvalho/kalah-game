package com.eac.kalah.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eac.kalah.exceptions.BusinessException;
import com.eac.kalah.exceptions.ResourceNotFoundException;
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

        if (board.getWinner() != null) {
            throw new BusinessException("This game has already a winner - " + board.getWinner().getDescription());
        }

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

        this.verifyWinner(board);

        return board;
    }

    @Override
    public Board findById(String id) {
        Board board = repository.findById(id);

        if (board == null) {
            throw new ResourceNotFoundException("There is no board with the given id - " + id);
        }

        return board;
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

    private void verifyWinner(Board board) {
        int stonesPlayerOne = board.getPlayerOne().getPits().stream().mapToInt(Pit::getStones).sum();
        int stonesPlayerTwo = board.getPlayerTwo().getPits().stream().mapToInt(Pit::getStones).sum();

        if (stonesPlayerOne == 0) {
            board.setWinner(PlayerEnum.ONE);

            House house = board.getPlayerTwo().getHouse();
            house.setStones(house.getStones() + stonesPlayerTwo);
            board.getPlayerTwo().getPits().forEach(pit -> pit.setStones(0));
        } else if (stonesPlayerTwo == 0) {
            board.setWinner(PlayerEnum.TWO);

            House house = board.getPlayerOne().getHouse();
            house.setStones(house.getStones() + stonesPlayerOne);
            board.getPlayerOne().getPits().forEach(pit -> pit.setStones(0));
        }
    }

    private Player getCurrentPlayer(final Board board) {
        return board.getCurrentPlayer() == PlayerEnum.ONE ? board.getPlayerOne() : board.getPlayerTwo();
    }

    private PlayerEnum getOpponentPlayer(final PlayerEnum currentPlayer) {
        return currentPlayer == PlayerEnum.ONE ? PlayerEnum.TWO : PlayerEnum.ONE;
    }
}