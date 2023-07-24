package org.example.bnp_test.exception;

public class SoldeInsuffisantException extends Exception {
        public SoldeInsuffisantException() {
            super("Solde insuffisant pour cette transaction");
        }
}
