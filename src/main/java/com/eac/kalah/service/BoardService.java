package com.eac.kalah.service;

import com.eac.kalah.model.entity.Board;
import com.eac.kalah.model.entity.enums.PitEnum;

/**
 * Created by eduardo on 19/10/16.
 */
public interface BoardService {

    Board create();
    Board move(String boardId, PitEnum pitId);
    Board findById(String id);
}