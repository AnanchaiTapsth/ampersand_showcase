package com.example.ampersand02.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.example.ampersand02.payload.commons.PageObject;
import com.example.ampersand02.payload.commons.PageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ampersand02.common.Constants.RequestHeader.REQUEST_DATE;
import static com.example.ampersand02.common.Constants.RequestHeader.RESPONSE_DATE;
import static com.example.ampersand02.utils.Helper.timeFormatter;

public class ResponseHelper {

    private ResponseHelper() {
        throw new IllegalStateException("Utility class");
    }

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    private static final String MESSAGE = "message";
    private static final String STATUS = "status";
    private static final String DATA_LIST = "datas";

    public static ResponseEntity<Object> response(HttpStatus httpStatus, Object object) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            httpHeaders.add(REQUEST_DATE, request.getHeader(REQUEST_DATE));
        }

        httpHeaders.add(RESPONSE_DATE, LocalDateTime.now().format(timeFormatter));
        return ResponseEntity.status(httpStatus).headers(httpHeaders).body(object);
    }

    public static ResponseEntity<Object> success(String message) {
        Map<String, Object> data = new HashMap<>();
        data.put(MESSAGE, message);
        data.put(STATUS, true);
        return response(HttpStatus.OK, data);
    }

    public static ResponseEntity<Object> successWithData(String message, Object obj) {
        Map<String, Object> data = new HashMap<>();
        data.put(MESSAGE, message);
        data.put(STATUS, true);
        data.putAll(objectMapper.convertValue(obj, Map.class));
        return response(HttpStatus.OK, data);
    }

    public static ResponseEntity<Object> successWithList(String message, Object obj) {
        Map<String, Object> data = new HashMap<>();
        data.put(MESSAGE, message);
        data.put(STATUS, true);
        data.put(DATA_LIST, obj);
        return response(HttpStatus.OK, data);
    }

    public static ResponseEntity<Object> successPage(String message, Page<?> page) {
        PageObject pageable = new PageObject();
        pageable.setPage(page.getNumber());
        pageable.setSize(page.getSize());
        pageable.setTotalElements(page.getTotalElements());
        pageable.setTotalPages(page.getTotalPages());
        pageable.setLast(page.isLast());

        PageResponse pageResponse = new PageResponse();
        pageResponse.setStatus(true);
        pageResponse.setMessage(message);
        pageResponse.setPage(pageable);
        pageResponse.setDatas(page.getContent());
        return response(HttpStatus.OK, pageResponse);
    }

    public static ResponseEntity<Object> bad(String message) {
        Map<String, Object> data = new HashMap<>();
        data.put(MESSAGE, message);
        data.put(STATUS, false);
        return response(HttpStatus.BAD_REQUEST, data);
    }

    public static ResponseEntity<Object> authError(String message) {
        Map<String, Object> data = new HashMap<>();
        data.put(MESSAGE, message);
        data.put(STATUS, false);
        return response(HttpStatus.UNAUTHORIZED, data);
    }
}
