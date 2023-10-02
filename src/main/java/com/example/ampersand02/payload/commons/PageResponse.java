package com.example.ampersand02.payload.commons;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageResponse implements Serializable {
    private Boolean status;
    private String message;
    private PageObject page;
    private Object datas;
}
