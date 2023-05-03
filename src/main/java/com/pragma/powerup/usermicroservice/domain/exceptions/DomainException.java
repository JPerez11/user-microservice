package com.pragma.powerup.usermicroservice.domain.exceptions;

/**
 * @Author Jhoan Perez
 * Class to handle exception in case of not getting data
 * Extends RuntimeException to throw it at runtime
 */
public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}
