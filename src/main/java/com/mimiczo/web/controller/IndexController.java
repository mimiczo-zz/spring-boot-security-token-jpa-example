package com.mimiczo.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mimiczo on 15. 11. 7..
 */
@Slf4j
@RestController
public class IndexController {

	@RequestMapping(value = "/", method=RequestMethod.GET)
    public ResponseEntity index() {
        String message = "hello world";
		log.debug("{}", message);
        return new ResponseEntity(message, HttpStatus.OK);
    }
}