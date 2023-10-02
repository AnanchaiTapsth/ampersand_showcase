package com.example.ampersand02.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class Helper {
    private Helper() {
    }

    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static String covertInstantToString(Instant dateTime) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(dateTime, ZoneOffset.UTC);
        return localDateTime.format(timeFormatter);
    }

    public static String convertInstantToString(Instant date){
        Date parsedDate = Date.from(date);
        return  dateFormat.format(parsedDate);
    }

}
