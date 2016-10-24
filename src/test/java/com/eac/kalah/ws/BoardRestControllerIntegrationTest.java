package com.eac.kalah.ws;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.eac.kalah.Application;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.net.URL;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createNewBoardWithSuccess() throws Exception {
        // final URL file = Resources.getResource("requests/participate/success_create.json");
        // String request = Resources.toString(file, Charsets.UTF_8);

        mockMvc.perform(post("/api/boards")
                .contentType(contentType))
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
}