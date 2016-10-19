package com.eac.kalah.service.impl;

import com.eac.kalah.model.entity.Board;
import com.eac.kalah.model.entity.Pit;
import com.eac.kalah.model.entity.Player;
import com.eac.kalah.model.entity.enums.PitEnum;
import com.eac.kalah.model.entity.enums.PlayerEnum;
import com.eac.kalah.model.repository.BoardRepository;
import com.eac.kalah.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by eduardo on 19/10/16.
 */
@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository repository;

    @Override
    public Board create() {
        Board board = new Board(UUID.randomUUID().toString());
        board.setPlayerOne(new Player(PlayerEnum.PLAYER_1, pitInitializer()));
        board.setPlayerTwo(new Player(PlayerEnum.PLAYER_2, pitInitializer()));
        return repository.save(board);
    }

    @Override
    public Board findById(String id) {
        return repository.findById(id);
    }

    private List<Pit> pitInitializer() {
        List<Pit> pits = new ArrayList<>();

        for (PitEnum pitEnum : PitEnum.values()) {
            pits.add(new Pit(pitEnum));
        }

        return pits;
    }
}