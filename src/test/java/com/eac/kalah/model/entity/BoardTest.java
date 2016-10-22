package com.eac.kalah.model.entity;

import com.eac.kalah.model.entity.enums.PlayerEnum;
import com.eac.kalah.model.entity.enums.WinnerEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by eduardo on 19/10/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class BoardTest {

    @Test
    public void newInitialBoardWithSuccess() throws Exception {
        Board board = this.createBoard();

        assertNotNull(board.getId());
        assertNotNull(board.getPlayerOne());
        assertNotNull(board.getPlayerTwo());
        assertNotNull(board.getCreatedDate());
        assertNull(board.getWinner());
        assertThat(board.getCurrentPlayer(), is(PlayerEnum.ONE));
        assertThat(board.getPlayerOne().getPits().size(), is(6));
        assertThat(board.getPlayerTwo().getPits().size(), is(6));
        assertThat(board.getPlayerOne().getPits().stream().mapToInt(Pit::getStones).sum(), is(36));
        assertThat(board.getPlayerTwo().getPits().stream().mapToInt(Pit::getStones).sum(), is(36));
    }

    @Test
    public void assignWinnerToBoardWithSuccess() throws Exception {
        Board board = this.createBoard();

        board.setCurrentPlayer(PlayerEnum.TWO);
        board.setWinner(WinnerEnum.TWO);

        assertNotNull(board.getId());
        assertNotNull(board.getPlayerOne());
        assertNotNull(board.getPlayerTwo());
        assertNotNull(board.getCreatedDate());
        assertThat(board.getWinner(), is(WinnerEnum.TWO));
        assertThat(board.getCurrentPlayer(), is(PlayerEnum.TWO));
    }

    @Test
    public void equalsAndHashCodeWithSuccess() throws Exception {
        Board boardOne = this.createBoard();
        Board boardTwo = this.createBoard();

        assertFalse(boardOne.equals(boardTwo));
    }

    private Board createBoard() {
        Player playerOne = new Player(PlayerEnum.ONE);
        Player playerTwo = new Player(PlayerEnum.TWO);
        return new Board(playerOne, playerTwo);
    }
}