package com.mimiczo.service.impl;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mimiczo.configuration.annotaion.TxPrimary;
import com.mimiczo.configuration.annotaion.TxPrimaryRead;
import com.mimiczo.configuration.exception.GlobalError;
import com.mimiczo.domain.system.QUser;
import com.mimiczo.domain.system.User;
import com.mimiczo.repository.UserRepository;
import com.mimiczo.service.UserService;
import com.mimiczo.support.condition.SearchCondition;
import com.mimiczo.support.utils.GlobalAssert;
import com.mimiczo.support.view.PageStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by mimiczo on 15. 11. 7..
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * join Us
     *
     * @param user the user
     */
    @Override
    @TxPrimary
    public Long join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    /**
     * Validate duplicate user.
     *
     * @param user the user
     */
    public void validateDuplicateUser(User user) {
        User findUser = userRepository.findByUsername(user.getUsername());
        GlobalAssert.Null(findUser, GlobalError.USS_E001);
    }

    @Override
    @TxPrimaryRead
    public Page<User> getUsers(SearchCondition condition, PageStatus pageStatus) {
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();

        if (condition.hasName()) {
            builder.and(qUser.name.contains(condition.getNickname()));
        }
        if (null == pageStatus.getSort()) {
            pageStatus = pageStatus.addSort(new Sort(Sort.Direction.DESC
                    , Lists.newArrayList("createdDate", "id")));
        }
        return userRepository.findAll(builder, pageStatus);
    }

    @Override
    @TxPrimaryRead
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        GlobalAssert.notNull(user, GlobalError.DEF_E900);
        return user;
    }
}
