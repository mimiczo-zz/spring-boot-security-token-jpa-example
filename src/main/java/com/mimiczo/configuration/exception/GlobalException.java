package com.mimiczo.configuration.exception;

/**
 * Created by mimiczo on 15. 12. 17..
 */
public class GlobalException extends RuntimeException {

    private Integer httpStatus;
    private String field;

    public GlobalException() {
        super("GlobalException without message");
    }
    public GlobalException(Enum<?> code) {
        super(code.name());
    }
    public GlobalException(Enum<?> code, String field) {
        super(code.name());
        this.field = field;
    }
    public String getField() {
        return this.field;
    }
    public Integer getHttpStatus() {
        return this.httpStatus;
    }
}
