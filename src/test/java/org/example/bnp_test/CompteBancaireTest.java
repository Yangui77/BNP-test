package org.example.bnp_test;

import org.example.bnp_test.CompteBancaire;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompteBancaireTest {


    @ParameterizedTest
    @CsvSource({"1, 2"})
    void shouldCreateBankAccount(int numeroDeCompte, int solde) {
    CompteBancaire compteBancaire = new CompteBancaire(
            BigInteger.valueOf(numeroDeCompte),
            BigDecimal.valueOf(solde));
        assertEquals(
                BigInteger.valueOf(numeroDeCompte), compteBancaire.getNumeroDeCompte()
             );
        assertEquals(
                BigDecimal.valueOf(solde), compteBancaire.getSolde()
             );
    }
}
