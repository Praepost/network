package org.oril.exception;

import javax.naming.AuthenticationException;

public class UserFoundException extends AuthenticationException{

    public UserFoundException(final String msg) {
        super(msg);
    }
}