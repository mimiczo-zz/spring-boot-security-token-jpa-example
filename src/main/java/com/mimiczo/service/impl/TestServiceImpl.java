package com.mimiczo.service.impl;

import com.google.common.collect.Lists;
import com.mimiczo.support.condition.TestCondition;
import com.mysema.query.BooleanBuilder;
import com.mimiczo.configuration.annotaion.TxPrimary;
import com.mimiczo.configuration.annotaion.TxPrimaryRead;
import com.mimiczo.domain.QTest;
import com.mimiczo.domain.Test;
import com.mimiczo.repository.TestRepository;
import com.mimiczo.service.TestService;
import com.mimiczo.support.view.PageStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by mimiczo on 15. 11. 7..
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestRepository testRepository;

    @Override
    @TxPrimaryRead
    public Page<Test> getTests(TestCondition condition, PageStatus pageStatus) {
        BooleanBuilder builder = new BooleanBuilder();
        QTest qTest = QTest.test;
        if (condition.hasName()) {
            builder.and(qTest.name.contains(condition.getName()));
        }
        if (null == pageStatus.getSort()) {
            pageStatus = pageStatus.addSort(new Sort(Sort.Direction.DESC, Lists
                    .newArrayList("createdDate", "id")));
        }
        return testRepository.findAll(builder, pageStatus);
    }

    @Override
    @TxPrimaryRead
    public Test getTest(Long id) {
        return testRepository.findOne(id);
    }

    @Override
    @TxPrimary
    public Test save(Test test) throws Exception {
        return null;
    }

    @Override
    @TxPrimary
    public Test modify(Test test) {
        return null;
    }

    @Override
    @TxPrimary
    public void delete(Test test) {
    }
}
