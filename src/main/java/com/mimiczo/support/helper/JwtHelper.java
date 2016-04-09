/*
 * Copyright (c) 2016. by mimiczo
 * All rights reserved.
 */

package com.mimiczo.support.helper;

/**
 * com.mimiczo.support.helper
 * Created by mimiczo on 2016.04.09
 */
public interface JwtHelper<T> {

    String create(T t);
}