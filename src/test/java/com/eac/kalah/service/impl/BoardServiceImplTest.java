package com.eac.kalah.service.impl;

import com.eac.kalah.exceptions.BusinessException;
import com.eac.kalah.exceptions.ListNotFoundException;
import com.eac.kalah.exceptions.ResourceNotFoundException;
import com.eac.kalah.model.entity.Board;
import com.eac.kalah.model.entity.Pit;
import com.eac.kalah.model.entity.Player;
import com.eac.kalah.model.entity.enums.PitEnum;
import com.eac.kalah.model.entity.enums.PlayerEnum;
import com.eac.kalah.model.repository.BoardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by eduardo on 22/10/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class BoardServiceImplTest {

    @InjectMocks
    private BoardServiceImpl service;

    @Mock
    private BoardRepository repository;

    @Test
    public void createNewBoardWithSuccess() {
        when(repository.save(any(Board.class))).thenReturn(getBoard());

        Board board = service.create();

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
    public void findByIdWithSuccess() {
        Board boardMock = getBoard();
        String id = boardMock.getId();

        boardMock.setCurrentPlayer(PlayerEnum.TWO);

        when(repository.findById(id)).thenReturn(boardMock);

        Board board = service.findById(id);

        assertNotNull(board);
        assertThat(board.getCurrentPlayer(), is(PlayerEnum.TWO));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findByIdNotFound() {
        when(repository.findById("other id")).thenReturn(null);

        service.findById("id");
    }

    @Test
    public void findAllWithSuccess() {
        when(repository.findAll()).thenReturn(Collections.singletonList(getBoard()));

        Collection<Board> boards = service.findAll();

        assertNotNull(boards);
        assertThat(boards.size(), is(1));
    }

    @Test(expected = ListNotFoundException.class)
    public void findAllListNotFound() {
        when(repository.findAll()).thenReturn(null);

        service.findAll();
    }

    @Test
    public void initialMovePlayerOnePitThreeWithSuccess() {
        Board boardMock = getBoard();
        String id = boardMock.getId();

        when(repository.findById(id)).thenReturn(boardMock);

        Board board = service.move(id, PitEnum.THREE);

        assertNull(board.getWinner());
        assertThat(board.getCurrentPlayer(), is(PlayerEnum.TWO));
        assertThat(board.getPlayerOne().getHouse().getStones(), is(1));
        assertThat(board.getPlayerTwo().getHouse().getStones(), is(0));
        assertThat(board.getPlayerOne().getPits().get(PitEnum.ONE.getPosition()).getStones(), is(6));
        assertThat(board.getPlayerOne().getPits().get(PitEnum.TWO.getPosition()).getStones(), is(6));
        assertThat(board.getPlayerOne().getPits().get(PitEnum.THREE.getPosition()).getStones(), is(0));
        assertThat(board.getPlayerOne().getPits().get(PitEnum.FOUR.getPosition()).getStones(), is(7));
        assertThat(board.getPlayerOne().getPits().get(PitEnum.FIVE.getPosition()).getStones(), is(7));
        assertThat(board.getPlayerOne().getPits().get(PitEnum.SIX.getPosition()).getStones(), is(7));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.ONE.getPosition()).getStones(), is(7));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.TWO.getPosition()).getStones(), is(7));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.THREE.getPosition()).getStones(), is(6));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.FOUR.getPosition()).getStones(), is(6));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.FIVE.getPosition()).getStones(), is(6));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.SIX.getPosition()).getStones(), is(6));
        assertThat(board.getPlayerOne().getPits().stream().mapToInt(Pit::getStones).sum(), is(33));
        assertThat(board.getPlayerTwo().getPits().stream().mapToInt(Pit::getStones).sum(), is(38));
    }

    @Test
    public void initialMovePlayerOnePitOneWithSuccess() {
        Board boardMock = getBoard();
        String id = boardMock.getId();

        when(repository.findById(id)).thenReturn(boardMock);

        Board board = service.move(id, PitEnum.ONE);

        assertNull(board.getWinner());
        assertThat(board.getCurrentPlayer(), is(PlayerEnum.ONE));
        assertThat(board.getPlayerOne().getHouse().getStones(), is(1));
        assertThat(board.getPlayerTwo().getHouse().getStones(), is(0));
        assertThat(board.getPlayerOne().getPits().get(PitEnum.ONE.getPosition()).getStones(), is(0));
        assertThat(board.getPlayerOne().getPits().stream().mapToInt(Pit::getStones).sum(), is(35));
        assertThat(board.getPlayerTwo().getPits().stream().mapToInt(Pit::getStones).sum(), is(36));
    }

    @Test
    public void movePlayerOnePitThreeToEmptyHouseWithSuccess() {
        Board boardMock = getBoard();
        String id = boardMock.getId();

        boardMock.getPlayerOne().getPits().get(PitEnum.THREE.getPosition()).setStones(1);
        boardMock.getPlayerOne().getPits().get(PitEnum.FOUR.getPosition()).setStones(0);
        boardMock.getPlayerTwo().getPits().get(PitEnum.THREE.getPosition()).setStones(4);

        boardMock.getPlayerOne().getHouse().setStones(2);
        boardMock.getPlayerTwo().getHouse().setStones(3);

        when(repository.findById(id)).thenReturn(boardMock);

        Board board = service.move(id, PitEnum.THREE);

        assertNull(board.getWinner());
        assertThat(board.getCurrentPlayer(), is(PlayerEnum.TWO));
        assertThat(board.getPlayerOne().getHouse().getStones(), is(7));
        assertThat(board.getPlayerTwo().getHouse().getStones(), is(3));
        assertThat(board.getPlayerOne().getPits().get(PitEnum.THREE.getPosition()).getStones(), is(0));
        assertThat(board.getPlayerOne().getPits().get(PitEnum.FOUR.getPosition()).getStones(), is(0));
        assertThat(board.getPlayerOne().getPits().get(PitEnum.FIVE.getPosition()).getStones(), is(6));
        assertThat(board.getPlayerOne().getPits().get(PitEnum.SIX.getPosition()).getStones(), is(6));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.ONE.getPosition()).getStones(), is(6));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.TWO.getPosition()).getStones(), is(6));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.THREE.getPosition()).getStones(), is(0));
        assertThat(board.getPlayerOne().getPits().stream().mapToInt(Pit::getStones).sum(), is(24));
        assertThat(board.getPlayerTwo().getPits().stream().mapToInt(Pit::getStones).sum(), is(30));
    }

    @Test
    public void movePlayerTwoPitSixToEmptyHouseOneLoopWithSuccess() {
        Board boardMock = getBoard();
        String id = boardMock.getId();

        boardMock.getPlayerTwo().getPits().get(PitEnum.ONE.getPosition()).setStones(0);
        boardMock.getPlayerTwo().getPits().get(PitEnum.SIX.getPosition()).setStones(9);
        boardMock.getPlayerOne().getPits().get(PitEnum.SIX.getPosition()).setStones(8);

        boardMock.getPlayerOne().getHouse().setStones(5);
        boardMock.getPlayerTwo().getHouse().setStones(7);

        boardMock.setCurrentPlayer(PlayerEnum.TWO);

        when(repository.findById(id)).thenReturn(boardMock);

        Board board = service.move(id, PitEnum.SIX);

        assertNull(board.getWinner());
        assertThat(board.getCurrentPlayer(), is(PlayerEnum.ONE));
        assertThat(board.getPlayerOne().getHouse().getStones(), is(6));
        assertThat(board.getPlayerTwo().getHouse().getStones(), is(18));
        assertThat(board.getPlayerOne().getPits().get(PitEnum.SIX.getPosition()).getStones(), is(0));
        assertThat(board.getPlayerOne().getPits().get(PitEnum.FIVE.getPosition()).getStones(), is(7));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.ONE.getPosition()).getStones(), is(0));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.TWO.getPosition()).getStones(), is(6));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.SIX.getPosition()).getStones(), is(0));
        assertThat(board.getPlayerOne().getPits().stream().mapToInt(Pit::getStones).sum(), is(35));
        assertThat(board.getPlayerTwo().getPits().stream().mapToInt(Pit::getStones).sum(), is(24));
    }

    @Test
    public void movePlayerTwoPitFourWithSuccess() {
        Board boardMock = getBoard();
        String id = boardMock.getId();

        boardMock.getPlayerTwo().getPits().get(PitEnum.TWO.getPosition()).setStones(2);

        when(repository.findById(id)).thenReturn(boardMock);

        boardMock.setCurrentPlayer(PlayerEnum.TWO);

        Board board = service.move(id, PitEnum.TWO);

        assertNull(board.getWinner());
        assertThat(board.getCurrentPlayer(), is(PlayerEnum.ONE));
        assertThat(board.getPlayerOne().getHouse().getStones(), is(0));
        assertThat(board.getPlayerTwo().getHouse().getStones(), is(0));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.ONE.getPosition()).getStones(), is(6));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.TWO.getPosition()).getStones(), is(0));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.THREE.getPosition()).getStones(), is(7));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.FOUR.getPosition()).getStones(), is(7));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.FIVE.getPosition()).getStones(), is(6));
        assertThat(board.getPlayerTwo().getPits().get(PitEnum.SIX.getPosition()).getStones(), is(6));
        assertThat(board.getPlayerOne().getPits().stream().mapToInt(Pit::getStones).sum(), is(36));
        assertThat(board.getPlayerTwo().getPits().stream().mapToInt(Pit::getStones).sum(), is(32));
    }

    @Test
    public void movePlayerOnePitSixWinnerWithSuccess() {
        Board boardMock = getBoard();
        String id = boardMock.getId();

        boardMock.getPlayerOne().getPits().forEach(p -> p.setStones(0));
        boardMock.getPlayerTwo().getPits().forEach(p -> p.setStones(0));

        boardMock.getPlayerOne().getPits().get(PitEnum.SIX.getPosition()).setStones(2);
        boardMock.getPlayerTwo().getPits().get(PitEnum.THREE.getPosition()).setStones(5);

        boardMock.getPlayerOne().getHouse().setStones(40);
        boardMock.getPlayerTwo().getHouse().setStones(25);

        when(repository.findById(id)).thenReturn(boardMock);

        boardMock.setCurrentPlayer(PlayerEnum.ONE);

        Board board = service.move(id, PitEnum.SIX);

        assertThat(board.getWinner(), is(PlayerEnum.ONE));
        assertThat(board.getPlayerOne().getHouse().getStones(), is(41));
        assertThat(board.getPlayerTwo().getHouse().getStones(), is(31));
        assertThat(board.getPlayerOne().getPits().stream().mapToInt(Pit::getStones).sum(), is(0));
        assertThat(board.getPlayerTwo().getPits().stream().mapToInt(Pit::getStones).sum(), is(0));
    }

    @Test
    public void movePlayerTwoPitSixWinnerWithSuccess() {
        Board boardMock = getBoard();
        String id = boardMock.getId();

        boardMock.getPlayerOne().getPits().forEach(p -> p.setStones(0));
        boardMock.getPlayerTwo().getPits().forEach(p -> p.setStones(0));

        boardMock.getPlayerTwo().getPits().get(PitEnum.SIX.getPosition()).setStones(1);
        boardMock.getPlayerOne().getPits().get(PitEnum.SIX.getPosition()).setStones(1);

        boardMock.getPlayerOne().getHouse().setStones(50);
        boardMock.getPlayerTwo().getHouse().setStones(25);

        when(repository.findById(id)).thenReturn(boardMock);

        boardMock.setCurrentPlayer(PlayerEnum.TWO);

        Board board = service.move(id, PitEnum.SIX);

        assertThat(board.getWinner(), is(PlayerEnum.ONE));
        assertThat(board.getPlayerOne().getHouse().getStones(), is(51));
        assertThat(board.getPlayerTwo().getHouse().getStones(), is(26));
        assertThat(board.getPlayerOne().getPits().stream().mapToInt(Pit::getStones).sum(), is(0));
        assertThat(board.getPlayerTwo().getPits().stream().mapToInt(Pit::getStones).sum(), is(0));
    }

    @Test(expected = BusinessException.class)
    public void matchFinishedWithSuccess() {
        Board boardMock = getBoard();
        String id = boardMock.getId();

        boardMock.setWinner(PlayerEnum.ONE);

        when(repository.findById(id)).thenReturn(boardMock);

        service.move(id, PitEnum.ONE);
    }

    @Test(expected = BusinessException.class)
    public void validatePitWithSuccess() {
        Board boardMock = getBoard();
        String id = boardMock.getId();

        boardMock.getPlayerOne().getPits().get(PitEnum.THREE.getPosition()).setStones(0);

        when(repository.findById(id)).thenReturn(boardMock);

        service.move(id, PitEnum.THREE);
    }

    private Board getBoard() {
        Player playerOne = new Player(PlayerEnum.ONE);
        Player playerTwo = new Player(PlayerEnum.TWO);
        return new Board(playerOne, playerTwo);
    }
}