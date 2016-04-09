package com.mimiczo.support.view;

import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
* Created by mimiczo on 15. 11. 7..
*/
@Getter
public class PageStatus implements Pageable, Serializable {

    private final int pageNumber;
    private final int pageSize;
    private final Sort sort;
    private final Map<String, Object> attributes;
    private final String queryString;
    private final String pageableQueryString;
    private final String jsonString;
    private final String pageableJsonString;

    public PageStatus(
            int pageNumber, int pageSize, Sort sort,
            Map<String, Object> attributes,
            String queryString, String pageableQueryString,
            String jsonString, String pageableJsonString) {

        if (0 > pageNumber) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        }
        if (0 >= pageSize) {
            throw new IllegalArgumentException("Page size must not be less than or equal to zero!");
        }

        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
        this.attributes = attributes == null ? new HashMap<>() : attributes;
        this.queryString = queryString;
        this.pageableQueryString = pageableQueryString;
        this.jsonString = jsonString;
        this.pageableJsonString = pageableJsonString;
    }

    @Override
    public int getOffset() {
        return pageNumber * pageSize;
    }

    public PageStatus addSort(Sort sort) {
        return new PageStatus(pageNumber, pageSize, sort, attributes,
                queryString, pageableQueryString, jsonString, pageableJsonString);
    }

    @Override
    public Pageable next() {
        return new PageStatus(pageNumber + 1, getPageSize(), getSort(), getAttributes(), getQueryString(), getPageableQueryString(), getJsonString(), getPageableJsonString());
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? new PageStatus(pageNumber -1, getPageSize(), getSort(), getAttributes(), getQueryString(), getPageableQueryString(), getJsonString(), getPageableJsonString()): this;
    }

    @Override
    public Pageable first() {
        return new PageStatus(0, getPageSize(), getSort(), getAttributes(), getQueryString(), getPageableQueryString(), getJsonString(), getPageableJsonString());
    }

    @Override
    public boolean hasPrevious() {
        return pageNumber > 0;
    }
}