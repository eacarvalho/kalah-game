package com.eac.kalah.service.impl;

import com.eac.kalah.exceptions.BusinessException;
import com.eac.kalah.exceptions.ListNotFoundException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by eduardo on 19/10/16.
 */
@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int NUMBER_OF_PIT = 6;

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
    public Board findById(String id) {
        Board board = repository.findById(id);

        if (board == null) {
            throw new ResourceNotFoundException("There is no board with the given id - " + id);
        }

        return board;
    }

    @Override
    public Collection<Board> findAll() {
        Collection<Board> boards = repository.findAll();

        if (boards == null || boards.size() == ZERO) {
            throw new ListNotFoundException("There is no board yet, first create one");
        }

        return boards;
    }

    @Override
    public Board move(String boardId, PitEnum pitId) {
        Board board = findById(boardId);

        this.validateMatch(board);

        Player currentPlayer = getCurrentPlayer(board);
        List<Pit> pits = currentPlayer.getPits();
        Pit currentPit = pits.get(pitId.getPosition());
        int stones = currentPit.getStones();
        int position = currentPit.getId().getPosition() + ONE;

        this.validatePit(pitId, currentPit);

        currentPit.setStones(ZERO);

        while (stones > ZERO) {
            stones = moveStones(position, stones, board, currentPlayer.getId());
            position = ZERO;
        }

        this.verifyWinner(board);

        return board;
    }

    private int moveStones(int position, int stones, Board board, PlayerEnum currentPlayer) {
        Player player = this.getCurrentPlayer(board);

        board.setCurrentPlayer(this.getOpponentPlayer(currentPlayer));

        for (int i = position; i < NUMBER_OF_PIT; i++) {
            if (stones > ZERO) {
                Pit pit = player.getPits().get(i);
                pit.setStones(pit.getStones() + ONE);
                stones--;

                if (pit.getStones() == ONE && stones == ZERO) {
                    this.emptyHouse(board, currentPlayer, pit.getId().getPosition());
                }
            } else {
                break;
            }
        }

        if (stones > ZERO) {
            House house = player.getHouse();
            house.setStones(house.getStones() + ONE);
            stones--;

            if (stones == ZERO && player.getId() == currentPlayer) {
                this.extraMove(board, currentPlayer);
            }
        }

        return stones;
    }

    /**
     * The last seed falls in the store, so the player receives an extra move.
     *
     * @param board
     * @param currentPlayer
     */
    private void extraMove(Board board, PlayerEnum currentPlayer) {
        log.info("Extra move - " + currentPlayer.getDescription());

        board.setCurrentPlayer(currentPlayer);
    }

    /**
     * The last seed falls in an empty house on the player's side. The player collects the highlighted seeds from both
     * his house and the opposite house of his opponent and will move them to his store. The player's turn ends.
     *
     * @param board
     * @param currentPlayer
     * @param position
     */
    // TODO Restrição para o mesmo lado do current player
    private void emptyHouse(Board board, PlayerEnum currentPlayer, int position) {
        log.info("Empty house - " + currentPlayer.getDescription());

        Player player = null;
        int stones = 0;
        int oppositePosition = (NUMBER_OF_PIT - ONE) - position;

        if (currentPlayer == PlayerEnum.ONE) {
            List<Pit> pitsPlayerTwo = board.getPlayerTwo().getPits();

            player = board.getPlayerOne();
            stones = pitsPlayerTwo.get(oppositePosition).getStones();
            pitsPlayerTwo.get(oppositePosition).setStones(ZERO);
        } else {
            List<Pit> pitsPlayerOne = board.getPlayerOne().getPits();

            player = board.getPlayerTwo();
            stones = pitsPlayerOne.get(oppositePosition).getStones();
            pitsPlayerOne.get(oppositePosition).setStones(ZERO);
        }

        House house = player.getHouse();
        house.setStones(house.getStones() + stones + ONE);
        player.getPits().get(position).setStones(ZERO);
    }

    /**
     * When one player no longer has any seeds in any of their houses, the game ends. The other player moves all
     * remaining seeds to their store, and the player with the most seeds in their store wins.
     *
     * @param board
     */
    private void verifyWinner(Board board) {
        int stonesPlayerOne = board.getPlayerOne().getPits().stream().mapToInt(Pit::getStones).sum();
        int stonesPlayerTwo = board.getPlayerTwo().getPits().stream().mapToInt(Pit::getStones).sum();

        if (stonesPlayerOne == ZERO) {
            House house = board.getPlayerTwo().getHouse();
            house.setStones(house.getStones() + stonesPlayerTwo);
            board.getPlayerTwo().getPits().forEach(pit -> pit.setStones(ZERO));

            board.setWinner(house.getStones() >= board.getPlayerOne().getHouse().getStones() ? PlayerEnum.TWO : PlayerEnum.ONE);
            log.info("Winner - " + board.getWinner().getDescription());
        } else if (stonesPlayerTwo == ZERO) {
            House house = board.getPlayerOne().getHouse();
            house.setStones(house.getStones() + stonesPlayerOne);
            board.getPlayerOne().getPits().forEach(pit -> pit.setStones(ZERO));

            board.setWinner(house.getStones() >= board.getPlayerTwo().getHouse().getStones() ? PlayerEnum.ONE : PlayerEnum.TWO);
            log.info("Winner - " + board.getWinner().getDescription());
        }
    }

    private void validateMatch(Board board) {
        if (board.getWinner() != null) {
            throw new BusinessException("This game has already a winner - " + board.getWinner().getDescription());
        }
    }

    private void validatePit(PitEnum pitId, Pit currentPit) {
        if (currentPit.getStones() == ZERO) {
            throw new BusinessException("The pit " + pitId + " is already empty, try another pit");
        }
    }

    private Player getCurrentPlayer(final Board board) {
        return board.getCurrentPlayer() == PlayerEnum.ONE ? board.getPlayerOne() : board.getPlayerTwo();
    }

    private PlayerEnum getOpponentPlayer(final PlayerEnum currentPlayer) {
        return currentPlayer == PlayerEnum.ONE ? PlayerEnum.TWO : PlayerEnum.ONE;
    }
}