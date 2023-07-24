package org.example.bnp_test.exception;

import static org.example.bnp_test.exception.ExceptionMessages.SoldeInsuffisantErrorMessage;

public class SoldeInsuffisantException extends Exception {
        public SoldeInsuffisantException() {
            super(SoldeInsuffisantErrorMessage.getErrorMessage());
        }
}
