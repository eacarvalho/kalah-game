package com.eac.kalah.model.repository.impl;

import com.eac.kalah.model.entity.Board;
import com.eac.kalah.model.repository.BoardRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by eduardo on 19/10/16.
 */
@Repository
public class BoardRepositoryImpl implements BoardRepository {

    private static final Map<String, Board> boards = new ConcurrentHashMap<>();

    @Override
    public Board save(Board board) {
        boards.put(board.getId(), board);
        return board;
    }

    @Override
    public Board findById(String id) {
        return boards.get(id);
    }

    @Override
    public Collection<Board> findAll() {
        return boards.values();
    }
}