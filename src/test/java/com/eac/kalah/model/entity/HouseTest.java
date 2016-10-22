package com.eac.kalah.model.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by eduardo on 19/10/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class HouseTest {

    @Test
    public void newHouseWithSuccess() throws Exception {
        House houseOne = new House(0);
        House houseTwo = new House(1);

        assertThat(houseOne.getStones(), is(0));
        assertThat(houseTwo.getStones(), is(1));
    }

    @Test
    public void assignStoneToHouseWithSuccess() throws Exception {
        House houseOne = new House(0);

        houseOne.setStones(6);

        assertThat(houseOne.getStones(), is(6));
    }
}