package org.example.bnp_test.exception;

public enum ExceptionMessages {
    DecimalErrorMessage("Le solde ne peut pas avoir plus de deux chiffres après la virgule"),
    NegativeAmountErrorMessage("Le montant spécifié ne peut pas être négatif"),
    SoldeInsuffisantErrorMessage("Solde insuffisant pour cette transaction");

    private final String errorMessage;

    ExceptionMessages(String message) {
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
