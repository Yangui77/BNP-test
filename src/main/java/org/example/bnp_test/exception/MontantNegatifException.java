package org.example.bnp_test.exception;

import static org.example.bnp_test.exception.ExceptionMessages.NegativeAmountErrorMessage;

public class MontantNegatifException extends IllegalArgumentException {

    public MontantNegatifException() {
        super(NegativeAmountErrorMessage.getErrorMessage());
    }
}
