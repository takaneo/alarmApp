package com.example.takahiro.alarmapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Takahiro on 2015/12/20.
 */
public class aaaaaTest {

    private aaaaa insstanceAaa;

    @Before
    public void setUp() throws Exception {
        insstanceAaa = new aaaaa();
    }

    @Test
    public void testSum() throws Exception {
        //予想：1 + 5 で 6が返ってくるはず
        assertEquals(6, insstanceAaa.sum(1, 5), 0);
    }

    @Test
    public void testSubstract() throws Exception {
        assertEquals(2, insstanceAaa.substract(5, 3), 0);
    }
}