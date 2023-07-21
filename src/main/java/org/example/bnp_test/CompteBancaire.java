package org.example.bnp_test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CompteBancaire {

    private BigInteger numeroDeCompte;
    private BigDecimal solde;

    public CompteBancaire(BigInteger numeroDeCompte, BigDecimal solde) {
        this.numeroDeCompte = numeroDeCompte;
        this.solde = solde;
    }

    protected BigDecimal getSolde() {
        return solde;
    }

    protected BigInteger getNumeroDeCompte() {
        return numeroDeCompte;
    }
}