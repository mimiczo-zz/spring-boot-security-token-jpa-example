/*
 * Copyright (c) 2016. by mimiczo
 * All rights reserved.
 */

package com.mimiczo.web.controller;

import com.mimiczo.domain.system.User;
import com.mimiczo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by mimiczo on 15. 11. 7..
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/account", method = RequestMethod.POST)
    public ResponseEntity<User> account(@Valid User user) {
        userService.join(user);
        return new ResponseEntity<>(
                user
                , HttpStatus.CREATED);
    }
}