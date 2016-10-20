package com.eac.kalah.service;

import java.util.Collection;

import com.eac.kalah.model.entity.Board;
import com.eac.kalah.model.entity.enums.PitEnum;

/**
 * Created by eduardo on 19/10/16.
 */
public interface BoardService {

    Board create();
    Board findById(String id);
    Collection<Board> findAll();
    Board move(String boardId, PitEnum pitId);
}