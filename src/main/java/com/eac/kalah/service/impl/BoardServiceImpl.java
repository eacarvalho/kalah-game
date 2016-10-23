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
import com.eac.kalah.model.entity.enums.WinnerEnum;
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
    private static final int MAX_OF_PIT = 6;

    @Autowired
    private BoardRepository repository;

    @Override
    public Board create() {
        Player playerOne = new Player(PlayerEnum.ONE);
        Player playerTwo = new Player(PlayerEnum.TWO);
        Board board = new Board(playerOne, playerTwo);
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
        final Board board = this.findById(boardId);
        final Player currentPlayer = this.getCurrentPlayer(board);
        final Pit currentPit = currentPlayer.getPits().get(pitId.getPosition());

        this.validateMatch(board);

        this.validatePit(currentPit);

        this.moveStone(board, currentPit, currentPlayer);

        this.verifyWinnerRule(board);

        return board;
    }

    private void moveStone(final Board board, final Pit currentPit, final Player currentPlayer) {
        int stones = currentPit.getStones();
        int position = currentPit.getId().getPosition() + ONE;

        currentPit.setStones(ZERO);
        board.setCurrentPlayer(this.getOpponentPlayer(currentPlayer.getId()));

        PlayerEnum currentPlayerSide = currentPlayer.getId();

        while (stones > ZERO) {
            stones = this.arrangeStones(position, stones, board, currentPlayer.getId(), currentPlayerSide);
            position = ZERO;
            currentPlayerSide = this.getOpponentPlayer(currentPlayerSide);
        }
    }

    private int arrangeStones(int position, int stones, Board board, final PlayerEnum currentPlayer, final PlayerEnum currentPlayerSide) {
        Player player = this.getCurrentPlayerSide(board, currentPlayerSide);

        for (int pitId = position; pitId < MAX_OF_PIT; pitId++) {
            if (stones > ZERO) {
                Pit pit = player.getPits().get(pitId);
                pit.setStones(pit.getStones() + ONE);
                stones--;

                if (this.isAnEmptyHouse(stones, currentPlayer, player, pit)) {
                    this.emptyPitRule(board, currentPlayer, pit.getId().getPosition());
                }
            } else {
                return ZERO;
            }
        }

        if (stones > ZERO) {
            this.addStoneHouse(player, ONE);

            stones--;

            if (this.isAnExtraMove(stones, currentPlayer, player)) {
                this.extraMoveRule(board, currentPlayer);
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
    private void extraMoveRule(final Board board, final PlayerEnum currentPlayer) {
        log.info("Extra move - " + currentPlayer.getDescription());
        board.setCurrentPlayer(currentPlayer);
    }

    /**
     * The last seed falls in an empty pit on the player's side. The player collects the highlighted seeds from both
     * his pit and the opposite pit of his opponent and will move them to his store. The player's turn ends.
     *
     * @param board
     * @param currentPlayer
     * @param position
     */
    private void emptyPitRule(final Board board, final PlayerEnum currentPlayer, int position) {
        log.info("Empty house - " + currentPlayer.getDescription());

        Player player = null;
        int opponentStones = 0;
        int oppositePosition = (MAX_OF_PIT - ONE) - position;

        if (currentPlayer == PlayerEnum.ONE) {
            List<Pit> pitsPlayerTwo = board.getPlayerTwo().getPits();

            player = board.getPlayerOne();
            opponentStones = pitsPlayerTwo.get(oppositePosition).getStones();
            pitsPlayerTwo.get(oppositePosition).setStones(ZERO);
        } else {
            List<Pit> pitsPlayerOne = board.getPlayerOne().getPits();

            player = board.getPlayerTwo();
            opponentStones = pitsPlayerOne.get(oppositePosition).getStones();
            pitsPlayerOne.get(oppositePosition).setStones(ZERO);
        }

        this.addStoneHouse(player, (opponentStones + ONE));

        player.getPits().get(position).setStones(ZERO);
    }

    /**
     * When one player no longer has any seeds in any of their houses, the game ends. The other player moves all
     * remaining seeds to their store, and the player with the most seeds in their store wins.
     *
     * @param board
     */
    private void verifyWinnerRule(final Board board) {
        int stonesPlayerOne = board.getPlayerOne().getPits().stream().mapToInt(Pit::getStones).sum();
        int stonesPlayerTwo = board.getPlayerTwo().getPits().stream().mapToInt(Pit::getStones).sum();

        if (stonesPlayerOne == ZERO || stonesPlayerTwo == ZERO) {
            board.getPlayerOne().getPits().forEach(pit -> pit.setStones(ZERO));
            board.getPlayerTwo().getPits().forEach(pit -> pit.setStones(ZERO));

            this.addStoneHouse(board.getPlayerOne(), stonesPlayerOne);
            this.addStoneHouse(board.getPlayerTwo(), stonesPlayerTwo);

            if (board.getPlayerOne().getHouse().getStones() > board.getPlayerTwo().getHouse().getStones()) {
                board.setWinner(WinnerEnum.ONE);
            } else if (board.getPlayerOne().getHouse().getStones() < board.getPlayerTwo().getHouse().getStones()) {
                board.setWinner(WinnerEnum.TWO);
            } else {
                board.setWinner(WinnerEnum.DRAW);
            }

            log.info("Winner - " + board.getWinner());
        }
    }

    private void addStoneHouse(final Player player, int stone) {
        House house = player.getHouse();
        house.setStones(house.getStones() + stone);
    }

    private boolean isAnExtraMove(int stones, PlayerEnum currentPlayer, Player player) {
        return stones == ZERO && player.getId() == currentPlayer;
    }

    private boolean isAnEmptyHouse(int stones, PlayerEnum currentPlayer, Player player, Pit pit) {
        return stones == ZERO && player.getId() == currentPlayer && pit.getStones() == ONE;
    }

    private Player getCurrentPlayer(final Board board) {
        return board.getCurrentPlayer() == PlayerEnum.ONE ? board.getPlayerOne() : board.getPlayerTwo();
    }

    private Player getCurrentPlayerSide(final Board board, final PlayerEnum currentPlayerSide) {
        return board.getPlayerOne().getId() == currentPlayerSide ? board.getPlayerOne() : board.getPlayerTwo();
    }

    private PlayerEnum getOpponentPlayer(final PlayerEnum currentPlayer) {
        return currentPlayer == PlayerEnum.ONE ? PlayerEnum.TWO : PlayerEnum.ONE;
    }

    private void validateMatch(final Board board) {
        if (board.getWinner() != null) {
            throw new BusinessException("This game has already a winner - " + board.getWinner());
        }
    }

    private void validatePit(final Pit currentPit) {
        if (currentPit.getStones() == ZERO) {
            throw new BusinessException("The pit " + currentPit.getId() + " is already empty, try another pit");
        }
    }
}