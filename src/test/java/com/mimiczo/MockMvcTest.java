/*
 * Mimiczo
 * Copyright (c) 2016.
 */

package com.mimiczo;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * com.mimiczo
 * Created by mimiczo on 2016.03.04
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Application.class})
@WebAppConfiguration
public abstract class MockMvcTest {

    @Autowired
    protected WebApplicationContext wac;
    protected MockMvc mockMvc;
    protected static final String CHARSET = "UTF-8";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .alwaysDo(MockMvcResultHandlers.print())//log in console
                .build();//mockmvc create
    }
}