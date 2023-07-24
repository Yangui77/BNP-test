package org.example.bnp_test;

import org.example.bnp_test.exception.MontantDecimalInvalideException;
import org.example.bnp_test.exception.MontantNegatifException;
import org.example.bnp_test.exception.SoldeInsuffisantException;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CompteBancaire {

    private final BigInteger numeroDeCompte;
    private BigDecimal solde;

    public CompteBancaire(BigInteger numeroDeCompte, BigDecimal solde) {
        checkValeurDecimal(solde);
        this.numeroDeCompte = numeroDeCompte;
        this.solde = solde;
    }

    /**
     * Permet de voir la valeur du solde
     */
    public BigDecimal getSolde() {
        return solde;
    }

    /**
     * Permet de voir le numéro de compte
     */
    protected BigInteger getNumeroDeCompte() {
        return numeroDeCompte;
    }

    /**
     * Permet de déposer un montant vers le solde.
     *
     * @param montant le montant à déposer
     */
    public void depot(BigDecimal montant) {
        checkValeurDecimal(montant);
        checkMontantPositif(montant);
        solde = solde.add(montant);
    }

    /**
     * Permet de retirer un montant du solde.
     *
     * @param montant le montant à retirer
     * @throws SoldeInsuffisantException si le solde n'est pas suffisant pour le retrait.
     */
    public void retrait(BigDecimal montant) throws SoldeInsuffisantException {
        checkValeurDecimal(montant);
        checkMontantPositif(montant);
        checkBalanceSuffisant(montant);
        solde = solde.subtract(montant);
    }


    /**
     * Permet de transférer un montant d'un compte vers un autre.
     *
     * @throws SoldeInsuffisantException si le solde n'est pas suffisant pour le transfert.
     */
    public void transferer(CompteBancaire destination, BigDecimal montant) throws SoldeInsuffisantException {
        retrait(montant);
        destination.depot(montant);
    }


    /**
     * Vérifie que le montant spéficié est positif.
     *
     * @param montant le montant à tester
     *                throws MontantNegatifException
     */
    private void checkMontantPositif(BigDecimal montant) throws MontantNegatifException {
        if (montant.compareTo(BigDecimal.ZERO) < 0) {
            throw new MontantNegatifException();
        }
    }

    /**
     * Vérifie si la valeur a un format correcte
     * throws MontantDecimalInvalideException
     */
    private void checkValeurDecimal(BigDecimal solde) throws MontantDecimalInvalideException {
        if (getNombredeChiffreApresLaVirgule(solde) > 2) {
            throw new MontantDecimalInvalideException();
        }
    }

    /**
     * Vérification du solde présent par rapport au montant demandé pour un retrait ou un transfert.
     * Renvoie une expception si le solde est insuffisant
     *
     * @param montant montant demandé
     *                throws SoldeInsuffisantException
     */
    private void checkBalanceSuffisant(BigDecimal montant) throws SoldeInsuffisantException {
        if (solde.compareTo(montant) < 0) {
            throw new SoldeInsuffisantException();
        }
    }

    /**
     * Prend un chiffre en bigdecimal en paramètre et retourne le nombre de chiffre après la virgule.
     *
     * @param bigDecimal bigdecimal number.
     * @return nom de chiffre après la virgule.
     */
    private int getNombredeChiffreApresLaVirgule(BigDecimal bigDecimal) {
        String string = bigDecimal.stripTrailingZeros().toPlainString();
        int index = string.indexOf(".");
        return index < 0 ? 0 : string.length() - index - 1;
    }
}