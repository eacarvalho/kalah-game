package com.eac.kalah.model.entity;

import com.eac.kalah.model.entity.enums.PitEnum;
import com.eac.kalah.model.entity.enums.PlayerEnum;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by eduardo on 19/10/16.
 */
public class PlayerTest {

    @Test
    public void newInitialPlayerWithSuccess() throws Exception {
        Player playerOne = new Player(PlayerEnum.ONE);
        Player playerTwo = new Player(PlayerEnum.TWO);

        assertThat(playerOne.getId().name(), is(PitEnum.ONE.name()));
        assertThat(playerOne.getPits().size(), is(6));
        assertThat(playerOne.getPits().get(0).getId(), is(PitEnum.ONE));
        assertThat(playerOne.getPits().get(0).getStones(), is(6));
        assertThat(playerOne.getHouse().getStones(), is(0));
        assertThat(playerTwo.getId().name(), is(PitEnum.TWO.name()));
        assertThat(playerTwo.getPits().size(), is(6));
        assertThat(playerTwo.getPits().get(0).getId(), is(PitEnum.ONE));
        assertThat(playerTwo.getPits().get(0).getStones(), is(6));
        assertThat(playerTwo.getHouse().getStones(), is(0));
    }

    @Test
    public void assignStoneToPlayerWithSuccess() throws Exception {
        Player playerOne = new Player(PlayerEnum.ONE);

        playerOne.getPits().get(PitEnum.TWO.getPosition()).setStones(0);
        playerOne.getPits().get(PitEnum.THREE.getPosition()).setStones(7);

        assertThat(playerOne.getId().name(), is(PitEnum.ONE.name()));
        assertThat(playerOne.getPits().size(), is(6));
        assertThat(playerOne.getPits().get(0).getStones(), is(6));
        assertThat(playerOne.getPits().get(1).getStones(), is(0));
        assertThat(playerOne.getPits().get(2).getStones(), is(7));
        assertThat(playerOne.getPits().get(3).getStones(), is(6));
        assertThat(playerOne.getPits().get(4).getStones(), is(6));
        assertThat(playerOne.getPits().get(5).getStones(), is(6));
        assertThat(playerOne.getHouse().getStones(), is(0));
    }
}