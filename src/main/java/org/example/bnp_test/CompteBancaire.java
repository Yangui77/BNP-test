package org.example.bnp_test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CompteBancaire {

    private final BigInteger numeroDeCompte;
    private BigDecimal solde;

    public static final String DecimalErrorMessage = "Le solde ne peut pas avoir plus de deux chiffres après la virgule";
    public static final String NegativeAmountErrorMessage = "Le montant spécifié ne peut pas être négatif";

    public CompteBancaire(BigInteger numeroDeCompte, BigDecimal solde) {
        checkValeurDecimal(solde);
        this.numeroDeCompte = numeroDeCompte;
        this.solde = solde;
    }

    /**
     * Vérifie si la valeur a un format correcte
     * throws IllegalArgumentException
     */
    private void checkValeurDecimal(BigDecimal solde) {
        if (getNumberOfDecimalPlaces(solde) > 2) {
            throw new IllegalArgumentException(
                    DecimalErrorMessage
            );
        }
    }

    public BigDecimal getSolde() {
        return solde;
    }

    protected BigInteger getNumeroDeCompte() {
        return numeroDeCompte;
    }

    public void depot(BigDecimal montant) {
        checkValeurDecimal(montant);
        checkMontantPositif(montant);
        solde = solde.add(montant);
    }

    public void retrait(BigDecimal montant) {
        checkValeurDecimal(montant);
        checkMontantPositif(montant);
        solde = solde.subtract(montant);
    }


    /**
     * Vérifie que le montant spéficié est positif
     * @param montant le montant à tester
     * throws IllegalArgumentException
     */
    private void checkMontantPositif(BigDecimal montant) throws IllegalArgumentException {
        if (montant.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(NegativeAmountErrorMessage);
        }
    }


    /**
     * Prend un chiffre en bigdecimal en paramètre et retourne le nombre de chiffre après la virgule.
     * @param bigDecimal bigdecimal number.
     * @return nom de chiffre après la virgule.
     */
    private int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
        String string = bigDecimal.stripTrailingZeros().toPlainString();
        int index = string.indexOf(".");
        return index < 0 ? 0 : string.length() - index - 1;
    }
}