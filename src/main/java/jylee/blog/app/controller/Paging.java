package jylee.blog.app.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
public class Paging {
    private final long back;
    private final long[] previous;
    private final long present;
    private final long[] after;
    private final long forward;
    private final String url;
}
