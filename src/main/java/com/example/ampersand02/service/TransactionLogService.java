package com.example.ampersand02.service;

import com.example.ampersand02.entity.TransactionLog;
import com.example.ampersand02.repository.TransactionLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@RestController
public class TransactionLogService {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    private LocalDateTime start ;

    private int httpStatus;

    public int checkHttp(int http){
        if(http == 400){
            this.httpStatus = HttpStatus.BAD_REQUEST.value();
        } else if(http == 500){
            this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
        else {
            this.httpStatus = HttpStatus.OK.value();
        }
        return this.httpStatus;
    }
    public void setStartTime(){
        this.start = LocalDateTime.now();
    }
    public LocalDateTime getStartTime(){
        return this.start;
    }
    public void savetransactionLog (String requestBody  , int httpStatus){
        List<String> saveLog = new ArrayList<>();
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setRequestBody(requestBody);
        transactionLog.setHttpStatus(httpStatus); // Assuming success
        transactionLog.setStartTime(LocalDateTime.now());
        transactionLog.setStartTime(getStartTime());
        transactionLog.setEndTime(LocalDateTime.now());

        saveLog.add("\n"+"RequestBody = "+transactionLog.getRequestBody() + "\n");
        saveLog.add("HttpStatus = "+transactionLog.getHttpStatus()+ "\n");
        saveLog.add("StartTime = "+transactionLog.getStartTime().toString()+ "\n");
        saveLog.add("EndTime = "+transactionLog.getEndTime().toString()+ "\n");

        log.info("saveLog = "+saveLog);
        jmsTemplate.convertAndSend("transaction_log", saveLog.toString());
        transactionLogRepository.save(transactionLog);

    }
}
