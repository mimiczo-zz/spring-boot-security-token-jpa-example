package com.mimiczo.service;

import com.mimiczo.domain.Test;
import com.mimiczo.support.condition.TestCondition;
import com.mimiczo.support.view.PageStatus;
import org.springframework.data.domain.Page;
/**
 * Created by mimiczo on 15. 11. 7..
 */
public interface TestService extends GeneralService<Test> {

    Page<Test> getTests(TestCondition condition, PageStatus pageStatus);

    Test getTest(Long id);
}