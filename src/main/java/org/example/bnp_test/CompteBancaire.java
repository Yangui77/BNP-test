package org.example.bnp_test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CompteBancaire {

    private BigInteger numeroDeCompte;
    private BigDecimal solde;

    public CompteBancaire(BigInteger numeroDeCompte, BigDecimal solde) {
        checkSolde(solde);
        this.numeroDeCompte = numeroDeCompte;
        this.solde = solde;
    }

    /**
     * Vérifie si le solde a un format correcte
     * throws IllegalArgumentException
     */
    private void checkSolde(BigDecimal solde) {
        System.out.println(solde);
        if (getNumberOfDecimalPlaces(solde) > 2) {
            throw new IllegalArgumentException(
                    "Le solde ne peut pas avoir plus de deux chiffres après la virgule"
            );
        }
    }

    protected BigDecimal getSolde() {
        return solde;
    }

    protected BigInteger getNumeroDeCompte() {
        return numeroDeCompte;
    }

    int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
        String string = bigDecimal.stripTrailingZeros().toPlainString();
        int index = string.indexOf(".");
        return index < 0 ? 0 : string.length() - index - 1;
    }
}