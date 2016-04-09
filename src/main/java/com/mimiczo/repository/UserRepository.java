package com.mimiczo.repository;

import com.mimiczo.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by mimiczo on 15. 12. 13..
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>
        , QueryDslPredicateExecutor<User> {

    User findByUsername(String username);
}
