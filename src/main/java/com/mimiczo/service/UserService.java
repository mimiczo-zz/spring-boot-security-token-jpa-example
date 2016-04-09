package com.mimiczo.service;

import com.mimiczo.domain.system.User;
import com.mimiczo.support.condition.SearchCondition;
import com.mimiczo.support.view.PageStatus;
import org.springframework.data.domain.Page;

/**
 * Created by mimiczo on 15. 12. 13..
 */
public interface UserService {

    Long join(User user);

    Page<User> getUsers(SearchCondition condition, PageStatus pageStatus);

    User getUser(String username);
}
