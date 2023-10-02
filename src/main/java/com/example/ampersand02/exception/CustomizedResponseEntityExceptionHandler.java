//package com.example.ampersand02.exception;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.example.ampersand02.common.Constants;
//import com.example.ampersand02.payload.commons.ApiResponse;
//import com.example.ampersand02.payload.commons.ErrorObj;
//import com.example.ampersand02.utils.ResponseHelper;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@Slf4j
//@RequiredArgsConstructor
//@RestControllerAdvice
//public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler implements AuthenticationEntryPoint {
//
//    private final ObjectMapper objectMapper;
//
//    @SneakyThrows
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//        ApiResponse resError = new ApiResponse();
//        resError.setStatus(false);
//        resError.setMessage(Constants.MESSAGE.ERR_AUTH_9999.getMsg());
//        resError.setErrors(new ArrayList<>(Collections.singletonList(new ErrorObj(Constants.MESSAGE.ERR_AUTH_0004.name(), Constants.MESSAGE.ERR_AUTH_0004.getMsg()))));
//        response.getWriter().write(objectMapper.writeValueAsString(resError));
//    }
//
//    @Override
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    protected ResponseEntity<Object> handleMissingServletRequestParameter(
//            MissingServletRequestParameterException ex, HttpHeaders headers,
//            HttpStatus status, WebRequest request) {
//
//        logger.error(ex.getMessage(), ex);
//
//        ApiResponse resError = new ApiResponse();
//        resError.setStatus(false);
//        resError.setMessage((ex.getMessage() != null && ex.getMessage().length() > 0) ? String.format("Parameter [%s] is missing.", ex.getParameterName()) : "Request is in malformed format or missing required parameter(s).");
//        resError.setErrors(new ArrayList<>());
//        return ResponseHelper.response(HttpStatus.BAD_REQUEST, resError);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ApiResponse resError = new ApiResponse();
//        resError.setStatus(false);
//        resError.setMessage(Constants.MESSAGE.ERR_AUTH_9999.getMsg());
//        resError.setErrors(new ArrayList<>());
//        return ResponseHelper.response(HttpStatus.BAD_REQUEST, resError);
//    }
//
//    @SneakyThrows
//    @ExceptionHandler(CommonErrorException.class)
//    public final ResponseEntity handleCommonErrorException(CommonErrorException ex, WebRequest request) {
//        ApiResponse resError = new ApiResponse();
//        resError.setStatus(false);
//        resError.setMessage(ex.getMessage());
//        resError.setErrors(ex.getErrors());
//        return ResponseHelper.response(ex.getHttpStatus(), resError);
//    }
//
//    @ExceptionHandler(ErrorMessageException.class)
//    public final ResponseEntity handleErrorMessageException(ErrorMessageException ex, WebRequest request) {
//        List<ErrorObj> errors = new ArrayList<>();
//
//        if (ex.getCode() != null) {
//            ErrorObj error = new ErrorObj();
//            error.setCode(ex.getCode().name());
//            error.setDescription(ex.getCode().getMsg());
//            errors.add(error);
//        }
//
//        ApiResponse resError = new ApiResponse();
//        resError.setStatus(false);
//        resError.setMessage(Constants.MESSAGE.ERR_AUTH_9999.getMsg());
//        resError.setErrors(errors);
//
//        return ResponseHelper.response(ex.getHttpStatus(), resError);
//    }
//
//    @ExceptionHandler(BadRequestException.class)
//    public final ResponseEntity handleBadRequestException(Exception ex, WebRequest request) {
//        log.error(ex.getMessage(), ex);
//
//        ApiResponse resError = new ApiResponse();
//        resError.setStatus(false);
//        resError.setMessage(ex.getMessage() != null && ex.getMessage().length() > 0 ? ex.getLocalizedMessage() : "error");
//        resError.setErrors(new ArrayList<>());
//        return ResponseHelper.response(HttpStatus.BAD_REQUEST, resError);
//    }
//
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public final ResponseEntity handleDataIntegrityViolationException(Exception ex, WebRequest request) {
//        log.error(ex.getMessage(), ex);
//
//        ApiResponse resError = new ApiResponse();
//        resError.setStatus(false);
//        resError.setMessage(Constants.MESSAGE.ERR_AUTH_0000.getMsg());
//        return ResponseHelper.response(HttpStatus.BAD_REQUEST, resError);
//    }
//
//}
