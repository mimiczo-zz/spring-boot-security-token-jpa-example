package com.mimiczo.support.view;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Collections;
import java.util.List;

/**
 * Created by mimiczo on 15. 11. 7..
 */
public class Paginations {

    private final static ModelMapper modelMapper = new ModelMapper();

    public static Pagination<Object> empty(Pageable pageable) {
        return new Pagination<Object>(Collections.emptyList(), pageable.getPageNumber(),
                pageable.getPageSize(), pageable.getSort(), 0);
    }

    public static <D> Pagination<D> pagination(List<D> contents, Pageable pageable) {
        return new Pagination<D>(contents, pageable.getPageNumber(), pageable.getPageSize(),
                pageable.getSort(), contents.size());
    }

    public static <D> Pagination<D> pagination(Page<D> page) {
        return new Pagination<D>(page.getContent(), page.getNumber(), page.getSize(),
                page.getSort(), page.getTotalElements());
    }

    public static <D> Pagination<D> transform(Page<?> page, Class<D> destinationType) {
        List<D> content = null;
        if (page.getContent().isEmpty()) {
            content = Collections.emptyList();
        } else {
            content = Lists.newArrayList(Lists.transform(page.getContent(), new Function<Object, D>() {
                @Override
                public D apply(Object input) {
                    return modelMapper.map(input, destinationType);
                }
            }));
        }

        return new Pagination<D>(content, page.getNumber(), page.getSize(), page.getSort(),
                page.getTotalElements());
    }
}
