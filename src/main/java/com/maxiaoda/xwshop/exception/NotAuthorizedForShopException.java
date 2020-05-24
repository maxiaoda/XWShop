package com.maxiaoda.xwshop.exception;

public class NotAuthorizedForShopException extends RuntimeException{
    public NotAuthorizedForShopException(String message) {
        super(message);
    }
}
