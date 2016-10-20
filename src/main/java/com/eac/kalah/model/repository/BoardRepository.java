package com.eac.kalah.model.repository;

import java.util.Collection;

import com.eac.kalah.model.entity.Board;

/**
 * Created by eduardo on 19/10/16.
 */
public interface BoardRepository {

    Board save(Board board);
    Board findById(String id);
    Collection<Board> findAll();
}