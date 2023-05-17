package com.example.CalendarApp.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {

    @JsonProperty("httpCode")
    private Integer httpCode;

    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("message")
    private String message;

    Error(final Integer httpCode, final String errorCode, final String message){
        this.httpCode = httpCode;
        this.errorCode = errorCode;
        this.message = message;
    }
    public static Error.ErrorBuilder builder() {
        return new Error.ErrorBuilder();
    }

    public Integer getHttpCode() {
        return this.httpCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public static class ErrorBuilder {

        private Integer httpCode;
        private String errorCode;
        private String message;

        ErrorBuilder() {
        }

        @JsonProperty("httpCode")
        public Error.ErrorBuilder httpCode(final Integer httpCode) {
            this.httpCode = httpCode;
            return this;
        }

        @JsonProperty("errorCode")
        public Error.ErrorBuilder errorCode(final String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        @JsonProperty("message")
        public Error.ErrorBuilder message(final String message) {
            this.message = message;
            return this;
        }

        public Error build() {
            return new Error(this.httpCode, this.errorCode, this.message);
        }

        public String toString() {
            return "Error.ErrorBuilder(httpCode=" + this.httpCode + ", errorCode=" + this.errorCode
                    + ", message=" + this.message + ")";
        }
    }
}
