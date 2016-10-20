package com.eac.kalah.ws.v1;

import com.eac.kalah.model.entity.Board;
import com.eac.kalah.model.entity.enums.PitEnum;
import com.eac.kalah.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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

    @RequestMapping(value = "/{boardId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Board> read(@PathVariable("boardId") @NotNull String boardId) {
        return ResponseEntity.ok().body(boardService.findById(boardId));
    }

    @RequestMapping(value = "/{boardId}/pits/{pitId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Board> update(@PathVariable("boardId") @NotNull String boardId, @PathVariable("pitId") @NotNull PitEnum pitId) {
        return ResponseEntity.ok().body(boardService.move(boardId, pitId));
    }
}