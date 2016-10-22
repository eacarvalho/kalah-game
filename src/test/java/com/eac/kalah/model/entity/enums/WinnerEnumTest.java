package com.eac.kalah.model.entity.enums;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by eduardo on 22/10/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class WinnerEnumTest {

    @Test
    public void checkWinnerWithSuccess() throws Exception {
        assertThat(WinnerEnum.ONE.name(), is("ONE"));
        assertThat(WinnerEnum.TWO.name(), is("TWO"));
        assertThat(WinnerEnum.DRAW.name(), is("DRAW"));
    }
}