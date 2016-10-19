package com.eac.kalah.service;

import com.eac.kalah.model.entity.Board;

/**
 * Created by eduardo on 19/10/16.
 */
public interface BoardService {

    Board create();
    Board findById(String id);
}