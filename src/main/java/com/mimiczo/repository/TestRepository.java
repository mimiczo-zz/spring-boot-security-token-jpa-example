package com.mimiczo.repository;

import com.mimiczo.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by mimiczo on 15. 11. 7..
 */
@Repository
public interface TestRepository extends JpaRepository<Test, Long>
        , QueryDslPredicateExecutor<Test> {

    Test findByName(String name);
}