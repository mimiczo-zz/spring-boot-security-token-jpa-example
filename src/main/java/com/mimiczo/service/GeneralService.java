package com.mimiczo.service;

/**
 * Created by mimiczo on 15. 11. 7..
 */
public interface GeneralService <T> {
    T save(T t) throws Exception;

    T modify(T t);

    void delete(T t);
}