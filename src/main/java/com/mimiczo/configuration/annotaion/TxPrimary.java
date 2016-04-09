package com.mimiczo.configuration.annotaion;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mimiczo on 15. 12. 13..
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(value="transactionManager"
        , rollbackFor=Exception.class
        , propagation= Propagation.REQUIRED
        , readOnly=false)
public @interface TxPrimary {
}
