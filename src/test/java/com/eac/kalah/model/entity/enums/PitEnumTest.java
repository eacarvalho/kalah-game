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
public class PitEnumTest {

    @Test
    public void checkPositionWithSuccess() throws Exception {
        assertThat(PitEnum.ONE.getPosition(), is(0));
        assertThat(PitEnum.TWO.getPosition(), is(1));
        assertThat(PitEnum.THREE.getPosition(), is(2));
        assertThat(PitEnum.FOUR.getPosition(), is(3));
        assertThat(PitEnum.FIVE.getPosition(), is(4));
        assertThat(PitEnum.SIX.getPosition(), is(5));
    }

    @Test
    public void getPitEnumByPositionWithSuccess() throws Exception {
        assertThat(PitEnum.get(0), is(PitEnum.ONE));
        assertThat(PitEnum.get(1), is(PitEnum.TWO));
        assertThat(PitEnum.get(2), is(PitEnum.THREE));
        assertThat(PitEnum.get(3), is(PitEnum.FOUR));
        assertThat(PitEnum.get(4), is(PitEnum.FIVE));
        assertThat(PitEnum.get(5), is(PitEnum.SIX));
    }

    @Test
    public void getPitEnumByPositionWithNoResult() throws Exception {
        assertNull(PitEnum.get(7));
    }
}