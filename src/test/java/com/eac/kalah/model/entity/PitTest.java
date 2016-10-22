package com.eac.kalah.model.entity;

import com.eac.kalah.model.entity.enums.PitEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by eduardo on 22/10/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class PitTest {

    @Test
    public void newInitialPitWithSuccess() throws Exception {
        Pit pitOne = new Pit(PitEnum.ONE);
        Pit pitTwo = new Pit(PitEnum.TWO);

        assertThat(pitOne.getId(), is(PitEnum.ONE));
        assertThat(pitOne.getStones(), is(6));
        assertThat(pitTwo.getId(), is(PitEnum.TWO));
        assertThat(pitTwo.getStones(), is(6));
    }

    @Test
    public void assignStoneToPitWithSuccess() throws Exception {
        Pit pitOne = new Pit(PitEnum.ONE);
        Pit pitTwo = new Pit(PitEnum.TWO);

        pitOne.setStones(0);
        pitTwo.setStones(7);

        assertThat(pitOne.getId(), is(PitEnum.ONE));
        assertThat(pitOne.getStones(), is(0));
        assertThat(pitTwo.getId(), is(PitEnum.TWO));
        assertThat(pitTwo.getStones(), is(7));
    }
}