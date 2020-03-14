/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ibcsprimax.hrm.common.error;

public class ErrorResponse {
    private int responseCode;
    private String error;
    private String exception;
    private String message;
    private String path;


    public ErrorResponse(int responseCode, String error, String message) {
        this.responseCode = responseCode;
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(int responseCode, String error, String exception, String message, String path) {
        this.responseCode = responseCode;
        this.error = error;
        this.exception = exception;
        this.message = message;
        this.path = path;

    }
    
    public int getStatus() {
        return responseCode;
    }

    public void setStatus(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    
    
}

