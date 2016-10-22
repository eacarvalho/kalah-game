package com.eac.kalah.model.repository.impl;

import com.eac.kalah.model.entity.Board;
import com.eac.kalah.model.entity.Player;
import com.eac.kalah.model.entity.enums.PlayerEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by eduardo on 22/10/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class BoardRepositoryImplTest {

    @InjectMocks
    private BoardRepositoryImpl repository;

    @Test
    public void saveBoardWithSuccess() throws Exception {
        Board boardMock = getBoard();

        Board board = repository.save(boardMock);

        assertNotNull(board);
        assertNull(board.getWinner());
        assertThat(board.getCurrentPlayer(), is(PlayerEnum.ONE));
        assertThat(board.getId(), is(boardMock.getId()));
        assertThat(board.getPlayerOne().getHouse().getStones(), is(boardMock.getPlayerOne().getHouse().getStones()));
    }

    @Test
    public void findByIdWithSuccess() throws Exception {
        Board boardMock = getBoard();

        repository.save(boardMock);

        Board board = repository.findById(boardMock.getId());

        assertNotNull(board);
        assertNull(board.getWinner());
        assertThat(board.getCurrentPlayer(), is(PlayerEnum.ONE));
        assertThat(board.getId(), is(boardMock.getId()));
        assertThat(board.getPlayerOne().getHouse().getStones(), is(boardMock.getPlayerOne().getHouse().getStones()));
    }

    @Test
    public void findByIdWithNoResult() throws Exception {
        Board boardMock = getBoard();

        repository.save(boardMock);

        Board board = repository.findById("Other Id");

        assertNull(board);
    }

    @Test
    public void findAllWithSuccess() throws Exception {
        Board boardMock = getBoard();

        repository.save(boardMock);

        Collection<Board> boards = repository.findAll();

        assertNotNull(boards);
        assertThat(boards.size(), greaterThan(0));
    }

    private Board getBoard() throws IOException, URISyntaxException {
        Player playerOne = new Player(PlayerEnum.ONE);
        Player playerTwo = new Player(PlayerEnum.TWO);
        return new Board(playerOne, playerTwo);
    }
}