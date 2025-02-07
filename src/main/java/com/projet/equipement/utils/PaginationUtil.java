package com.projet.equipement.utils;

import org.springframework.data.domain.*;

import java.util.List;


public class PaginationUtil {

    /**
     *
     * @param list
     * @param pageable
     * @return page
     * @param <T>
     */
    public static <T> Page<T> toPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        List<T> subList = list.subList(start, end);
        return new PageImpl<>(subList, pageable, list.size());
    }
}