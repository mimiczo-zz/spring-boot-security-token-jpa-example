package com.mimiczo.web.controller;

import com.mimiczo.domain.Test;
import com.mimiczo.service.TestService;
import com.mimiczo.support.condition.TestCondition;
import com.mimiczo.support.view.PageStatus;
import com.mimiczo.support.view.Pagination;
import com.mimiczo.support.view.Paginations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * Created by mimiczo on 15. 11. 7..
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/tests", method = RequestMethod.GET)
    public ResponseEntity<Pagination<Test>> list(TestCondition condition, PageStatus pageStatus) {
        Page<Test> page = testService.getTests(condition, pageStatus);
        return new ResponseEntity<>(
                    Paginations.pagination(page.getContent(), pageStatus)
                    , HttpStatus.OK);
    }

    @RequestMapping(value = "/tests", method = RequestMethod.POST)
    public ResponseEntity<Test> add(@Valid @RequestBody Test test
                      , BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                    testService.save(test)
                    , HttpStatus.CREATED);
    }

    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    public ResponseEntity<Test> list(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                    testService.getTest(id)
                    , HttpStatus.OK);
    }
}
