package jylee.blog.app.controller;

import jylee.blog.app.dto.PostResponse;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
public class CustomPage {
    private final List<PostResponse> content;
    private final Pageable pageable;
    private final boolean last;
    private final long totalElements;
    private final int totalPages;
    private final int size;
    private final int number;
    private final Sort sort;
    private final boolean first;
    private final int numberOfElements;
    private final boolean empty;

    public CustomPage(Page<PostResponse> page) {
        content = page.getContent();
        pageable = page.getPageable();
        last = page.isLast();
        totalElements = page.getTotalElements();
        totalPages = page.getTotalPages();
        size = page.getSize();
        number = page.getNumber() + 1;
        sort = page.getSort();
        first = page.isFirst();
        numberOfElements = page.getNumberOfElements();
        empty = page.isEmpty();
    }
}
