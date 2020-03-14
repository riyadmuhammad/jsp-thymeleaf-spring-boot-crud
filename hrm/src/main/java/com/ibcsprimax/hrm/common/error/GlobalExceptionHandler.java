
package com.ibcsprimax.hrm.common.error;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler {

	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(HttpServletRequest req, Exception e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), e.getClass().getName(), "Failed for Bad Request, Missing Data", req.getRequestURL().toString());
        logger.error("Error URL: " + req.getRequestURL().toString()  + " Error Class: " + e.getClass().getName() + "...Message: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(HttpServletRequest req, Exception e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), e.getClass().getName(), "Failed for Invalid Data or Unauthorized", req.getRequestURL().toString());
        logger.error("Error URL: " + req.getRequestURL().toString()   + "  Error Class: " + e.getClass().getName() + "...Message: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(HttpServletRequest req, Exception e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.name(), e.getClass().getName(), "Failed For Data Integrity Violation", req.getRequestURL().toString());
        logger.error("Error URL: " + req.getRequestURL().toString()  + "  Error Class: " + e.getClass().getName() + "...Message: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);

    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpServletRequest req, Exception e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), e.getClass().getName(), e.getMessage(), req.getRequestURL().toString());
        logger.error("Error URL: " + req.getRequestURL().toString() +  " Error Class: " + e.getClass().getName() + "...Message: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(HttpServletRequest req, Exception e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), e.getClass().getName(), e.getMessage(), req.getRequestURL().toString());
        logger.error("Error URL: " + req.getRequestURL().toString()  + " Error Class: " + e.getClass().getName() + "...Message: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(HttpServletRequest req, Exception e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.name(), e.getClass().getName(), "Failed", req.getRequestURL().toString());
        logger.error("Error URL: " + req.getRequestURL().toString()  + " Error Class: " + e.getClass().getName() + "...Message: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleBrokenPipeException(HttpServletRequest request, IOException e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), HttpStatus.SERVICE_UNAVAILABLE.name(), e.getClass().getName(), "Failed", request.getRequestURL().toString());
        logger.error("Error URL: " + request.getRequestURL().toString() + " Error Class: " + e.getClass().getName() + "...Message: " + e.getMessage());
        if (e.getClass().getName().equals("org.apache.catalina.connector.ClientAbortException")) {
            return null;
        }
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(HttpServletRequest req, Exception e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE.name(), e.getClass().getName(), "Failed", req.getRequestURL().toString());
        logger.error("Error URL: " + req.getRequestURL().toString() + " Error Class: " + e.getClass().getName() + "...Message: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }
}
