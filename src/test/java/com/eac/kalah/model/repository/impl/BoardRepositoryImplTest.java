package com.eac.kalah.model.repository.impl;

import com.eac.kalah.model.entity.Board;
import com.eac.kalah.model.entity.enums.PlayerEnum;
import com.eac.kalah.util.JsonHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

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
        assertThat(boards.size(), is(1));
    }

    private Board getBoard() throws IOException, URISyntaxException {
        return new JsonHelper().fromJson(readJson(), Board.class);
    }

    private String readJson() throws IOException, URISyntaxException {
        URL invoiceURL = this.getClass().getClassLoader().getResource("json/kalah-new-game.json");
        byte[] encoded = Files.readAllBytes(Paths.get(invoiceURL.toURI()));
        return new String(encoded, "UTF-8");
    }
}