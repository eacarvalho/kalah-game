package com.eac.kalah.model.entity.enums;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by eduardo on 22/10/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerEnumTest {

    @Test
    public void checkDescriptionWithSuccess() throws Exception {
        assertThat(PlayerEnum.ONE.getDescription(), is("Player 1"));
        assertThat(PlayerEnum.TWO.getDescription(), is("Player 2"));
    }

    @Test
    public void getPitEnumByPositionWithSuccess() throws Exception {
        assertThat(PlayerEnum.get("Player 1"), is(PlayerEnum.ONE));
        assertThat(PlayerEnum.get("Player 2"), is(PlayerEnum.TWO));
    }

    @Test
    public void getPitEnumByPositionIgnoreCaseWithSuccess() throws Exception {
        assertThat(PlayerEnum.get("player 1"), is(PlayerEnum.ONE));
    }

    @Test
    public void getPitEnumByPositionWithNoResult() throws Exception {
        assertNull(PlayerEnum.get("X"));
    }
}