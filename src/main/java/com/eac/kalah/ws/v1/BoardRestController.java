package com.eac.kalah.ws.v1;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eac.kalah.model.entity.Board;
import com.eac.kalah.model.entity.enums.PitEnum;
import com.eac.kalah.service.BoardService;

@RestController
@RequestMapping("/boards")
public class BoardRestController {

    @Autowired
    private BoardService boardService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Board> create() {
        return ResponseEntity.ok().body(boardService.create());
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Board>> findAll() {
        return ResponseEntity.ok().body(boardService.findAll());
    }

    @RequestMapping(value = "/{boardId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Board> find(@PathVariable("boardId") @NotNull String boardId) {
        return ResponseEntity.ok().body(boardService.findById(boardId));
    }

    @RequestMapping(value = "/{boardId}/pits/{pitId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Board> update(@PathVariable("boardId") @NotNull String boardId, @PathVariable("pitId") @NotNull PitEnum pitId) {
        return ResponseEntity.ok().body(boardService.move(boardId, pitId));
    }
}