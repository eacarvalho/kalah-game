package com.eac.kalah.ws;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.eac.kalah.Application;
import com.eac.kalah.model.entity.Board;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by ext.eduardo.carvalho on 24/10/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class BoardRestControllerIntegrationTest {

    MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createNewBoardWithSuccess() throws Exception {
        mockMvc.perform(post("/api/boards")
                .contentType(contentType))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.playerOne.pits[0].id").value("ONE"))
                .andExpect(jsonPath("$.playerOne.pits[0].stones").value(6))
                .andExpect(jsonPath("$.playerOne.pits[1].id").value("TWO"))
                .andExpect(jsonPath("$.playerOne.pits[1].stones").value(6))
                .andExpect(jsonPath("$.playerOne.pits[2].id").value("THREE"))
                .andExpect(jsonPath("$.playerOne.pits[2].stones").value(6))
                .andExpect(jsonPath("$.playerOne.pits[3].id").value("FOUR"))
                .andExpect(jsonPath("$.playerOne.pits[3].stones").value(6))
                .andExpect(jsonPath("$.playerOne.pits[4].id").value("FIVE"))
                .andExpect(jsonPath("$.playerOne.pits[4].stones").value(6))
                .andExpect(jsonPath("$.playerOne.pits[5].id").value("SIX"))
                .andExpect(jsonPath("$.playerOne.pits[5].stones").value(6))
                .andExpect(jsonPath("$.playerOne.house.stones").value(0))
                .andExpect(jsonPath("$.playerTwo.pits[0].id").value("ONE"))
                .andExpect(jsonPath("$.playerTwo.pits[0].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[1].id").value("TWO"))
                .andExpect(jsonPath("$.playerTwo.pits[1].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[2].id").value("THREE"))
                .andExpect(jsonPath("$.playerTwo.pits[2].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[3].id").value("FOUR"))
                .andExpect(jsonPath("$.playerTwo.pits[3].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[4].id").value("FIVE"))
                .andExpect(jsonPath("$.playerTwo.pits[4].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[5].id").value("SIX"))
                .andExpect(jsonPath("$.playerTwo.pits[5].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.house.stones").value(0))
                .andExpect(jsonPath("$.currentPlayer").value("ONE"))
                .andExpect(jsonPath("$.winner").doesNotExist())
                .andExpect(jsonPath("$.createdDate").exists())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void playerOneMoveStoneFromPitOneWithSuccess() throws Exception {
        final String jsonNewBoard = mockMvc.perform(post("/api/boards").contentType(contentType)).andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();
        final Board newBoard = mapper.readValue(jsonNewBoard, Board.class);

        mockMvc.perform(put("/api/boards/" + newBoard.getId() + "/pits/ONE")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.playerOne.pits[0].id").value("ONE"))
                .andExpect(jsonPath("$.playerOne.pits[0].stones").value(0))
                .andExpect(jsonPath("$.playerOne.pits[1].id").value("TWO"))
                .andExpect(jsonPath("$.playerOne.pits[1].stones").value(7))
                .andExpect(jsonPath("$.playerOne.pits[2].id").value("THREE"))
                .andExpect(jsonPath("$.playerOne.pits[2].stones").value(7))
                .andExpect(jsonPath("$.playerOne.pits[3].id").value("FOUR"))
                .andExpect(jsonPath("$.playerOne.pits[3].stones").value(7))
                .andExpect(jsonPath("$.playerOne.pits[4].id").value("FIVE"))
                .andExpect(jsonPath("$.playerOne.pits[4].stones").value(7))
                .andExpect(jsonPath("$.playerOne.pits[5].id").value("SIX"))
                .andExpect(jsonPath("$.playerOne.pits[5].stones").value(7))
                .andExpect(jsonPath("$.playerOne.house.stones").value(1))
                .andExpect(jsonPath("$.playerTwo.pits[0].id").value("ONE"))
                .andExpect(jsonPath("$.playerTwo.pits[0].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[1].id").value("TWO"))
                .andExpect(jsonPath("$.playerTwo.pits[1].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[2].id").value("THREE"))
                .andExpect(jsonPath("$.playerTwo.pits[2].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[3].id").value("FOUR"))
                .andExpect(jsonPath("$.playerTwo.pits[3].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[4].id").value("FIVE"))
                .andExpect(jsonPath("$.playerTwo.pits[4].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[5].id").value("SIX"))
                .andExpect(jsonPath("$.playerTwo.pits[5].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.house.stones").value(0))
                .andExpect(jsonPath("$.currentPlayer").value("ONE"))
                .andExpect(jsonPath("$.winner").doesNotExist())
                .andExpect(jsonPath("$.createdDate").exists())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void playerOneMoveStoneFromPitTwoWithSuccess() throws Exception {
        final String jsonNewBoard = mockMvc.perform(post("/api/boards").contentType(contentType)).andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();
        final Board newBoard = mapper.readValue(jsonNewBoard, Board.class);

        mockMvc.perform(put("/api/boards/" + newBoard.getId() + "/pits/TWO")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.playerOne.pits[0].id").value("ONE"))
                .andExpect(jsonPath("$.playerOne.pits[0].stones").value(6))
                .andExpect(jsonPath("$.playerOne.pits[1].id").value("TWO"))
                .andExpect(jsonPath("$.playerOne.pits[1].stones").value(0))
                .andExpect(jsonPath("$.playerOne.pits[2].id").value("THREE"))
                .andExpect(jsonPath("$.playerOne.pits[2].stones").value(7))
                .andExpect(jsonPath("$.playerOne.pits[3].id").value("FOUR"))
                .andExpect(jsonPath("$.playerOne.pits[3].stones").value(7))
                .andExpect(jsonPath("$.playerOne.pits[4].id").value("FIVE"))
                .andExpect(jsonPath("$.playerOne.pits[4].stones").value(7))
                .andExpect(jsonPath("$.playerOne.pits[5].id").value("SIX"))
                .andExpect(jsonPath("$.playerOne.pits[5].stones").value(7))
                .andExpect(jsonPath("$.playerOne.house.stones").value(1))
                .andExpect(jsonPath("$.playerTwo.pits[0].id").value("ONE"))
                .andExpect(jsonPath("$.playerTwo.pits[0].stones").value(7))
                .andExpect(jsonPath("$.playerTwo.pits[1].id").value("TWO"))
                .andExpect(jsonPath("$.playerTwo.pits[1].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[2].id").value("THREE"))
                .andExpect(jsonPath("$.playerTwo.pits[2].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[3].id").value("FOUR"))
                .andExpect(jsonPath("$.playerTwo.pits[3].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[4].id").value("FIVE"))
                .andExpect(jsonPath("$.playerTwo.pits[4].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[5].id").value("SIX"))
                .andExpect(jsonPath("$.playerTwo.pits[5].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.house.stones").value(0))
                .andExpect(jsonPath("$.currentPlayer").value("TWO"))
                .andExpect(jsonPath("$.winner").doesNotExist())
                .andExpect(jsonPath("$.createdDate").exists())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void playerOneTryMoveStoneFromEmptyPitWithError() throws Exception {
        final String jsonNewBoard = mockMvc.perform(post("/api/boards").contentType(contentType)).andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();
        final Board newBoard = mapper.readValue(jsonNewBoard, Board.class);

        mockMvc.perform(put("/api/boards/" + newBoard.getId() + "/pits/FOUR").contentType(contentType)).andExpect(status().isOk());
        mockMvc.perform(put("/api/boards/" + newBoard.getId() + "/pits/TWO").contentType(contentType)).andExpect(status().isOk());
        mockMvc.perform(put("/api/boards/" + newBoard.getId() + "/pits/FOUR").contentType(contentType)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors").exists());
    }

    @Test
    public void findOneBoardWithSuccess() throws Exception {
        final String jsonNewBoard = mockMvc.perform(post("/api/boards").contentType(contentType)).andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();
        final Board newBoard = mapper.readValue(jsonNewBoard, Board.class);

        mockMvc.perform(get("/api/boards/" + newBoard.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.playerOne.pits[0].id").value("ONE"))
                .andExpect(jsonPath("$.playerOne.pits[0].stones").value(6))
                .andExpect(jsonPath("$.playerOne.pits[1].id").value("TWO"))
                .andExpect(jsonPath("$.playerOne.pits[1].stones").value(6))
                .andExpect(jsonPath("$.playerOne.pits[2].id").value("THREE"))
                .andExpect(jsonPath("$.playerOne.pits[2].stones").value(6))
                .andExpect(jsonPath("$.playerOne.pits[3].id").value("FOUR"))
                .andExpect(jsonPath("$.playerOne.pits[3].stones").value(6))
                .andExpect(jsonPath("$.playerOne.pits[4].id").value("FIVE"))
                .andExpect(jsonPath("$.playerOne.pits[4].stones").value(6))
                .andExpect(jsonPath("$.playerOne.pits[5].id").value("SIX"))
                .andExpect(jsonPath("$.playerOne.pits[5].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[0].id").value("ONE"))
                .andExpect(jsonPath("$.playerTwo.pits[0].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[1].id").value("TWO"))
                .andExpect(jsonPath("$.playerTwo.pits[1].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[2].id").value("THREE"))
                .andExpect(jsonPath("$.playerTwo.pits[2].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[3].id").value("FOUR"))
                .andExpect(jsonPath("$.playerTwo.pits[3].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[4].id").value("FIVE"))
                .andExpect(jsonPath("$.playerTwo.pits[4].stones").value(6))
                .andExpect(jsonPath("$.playerTwo.pits[5].id").value("SIX"))
                .andExpect(jsonPath("$.playerTwo.pits[5].stones").value(6))
                .andExpect(jsonPath("$.currentPlayer").value("ONE"))
                .andExpect(jsonPath("$.winner").doesNotExist())
                .andExpect(jsonPath("$.createdDate").exists())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void boardNotFoundById() throws Exception {
        mockMvc.perform(get("/api/boards/any-id")).andExpect(status().isNotFound()).andExpect(jsonPath("$.errors").exists()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findAllBoardWithSuccess() throws Exception {
        mockMvc.perform(post("/api/boards").contentType(contentType)).andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();
        mockMvc.perform(get("/api/boards")).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }
}