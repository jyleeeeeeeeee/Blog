package jylee.blog.app.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Paging {
    private int back;
    private int[] previous;
    private int present;
    private int[] after;
    private int forward;
}
