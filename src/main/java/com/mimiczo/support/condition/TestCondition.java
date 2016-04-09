package com.mimiczo.support.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * Created by mimiczo on 15. 11. 7..
 */
@Data
@AllArgsConstructor
public class TestCondition {
    private Long id;
    private String name;

    public boolean hasName() {
        return StringUtils.hasText(name);
    }
}