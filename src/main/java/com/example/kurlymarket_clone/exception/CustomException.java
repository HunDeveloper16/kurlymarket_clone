package com.example.kurlymarket_clone.exception;

public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode){
        super(errorCode.getMessage()); // RuntimeException -> Exception -> Throwable에서 message를 매개변수로 받아 detailMessage에 저장한 뒤, getMessage()를 통해 detailMessage를 반환한다.
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return this.errorCode;
    }

}
