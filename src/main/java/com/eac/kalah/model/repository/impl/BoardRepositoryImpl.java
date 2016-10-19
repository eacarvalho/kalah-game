package com.eac.kalah.model.repository.impl;

import com.eac.kalah.model.entity.Board;
import com.eac.kalah.model.repository.BoardRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eduardo on 19/10/16.
 */
@Repository
public class BoardRepositoryImpl implements BoardRepository {

    private static final Map<String, Board> boards = new HashMap<>();

    @Override
    public Board save(Board board) {
        boards.put(board.getId(), board);
        return board;
    }

    @Override
    public Board findById(String id) {
        return boards.get(id);
    }
}