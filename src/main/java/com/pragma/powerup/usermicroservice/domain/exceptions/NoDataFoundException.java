package com.pragma.powerup.usermicroservice.domain.exceptions;

/**
 * @Author Jhoan Perez
 * Class to handle exception in case of not getting data
 * Extends RuntimeException to throw it at runtime
 */
public class NoDataFoundException extends RuntimeException {

    public NoDataFoundException(String message) {
        super(message);
    }
}
