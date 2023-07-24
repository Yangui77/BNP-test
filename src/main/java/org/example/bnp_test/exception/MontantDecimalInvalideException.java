package org.example.bnp_test.exception;


import static org.example.bnp_test.exception.ExceptionMessages.DecimalErrorMessage;

public class MontantDecimalInvalideException extends IllegalArgumentException {

    public MontantDecimalInvalideException() {
        super(DecimalErrorMessage.getErrorMessage());
    }
}
