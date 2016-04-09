/*
 * Mimiczo
 * Copyright (c) 2016.
 */

package com.mimiczo.mvc;

import com.mimiczo.MockMvcTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * com.mimiczo.mvc
 * Created by mimiczo on 2016.03.04
 */
@Slf4j
public class IndexControllerTest extends MockMvcTest {

    @Test
    public void index() throws Exception {
        //given
        String content = "";
        log.debug("{}", content);

        //when
        this.mockMvc
                .perform(post("/")
                                //example parameter setting
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(content)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .characterEncoding(CHARSET)
                )
        //then
                .andExpect(status().isCreated())
                .andReturn();
    }
}
