package org.example.bnp_test;

import org.example.bnp_test.CompteBancaire;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CompteBancaireTest {


    @ParameterizedTest
    @CsvSource({
            "1, 2",
            "213213213, 0",
            "213213213, -10",
            "213213213, -10,10"
    }

    )
    void shouldCreateBankAccountWithCorrectValues(int numeroDeCompte, int solde) {
        CompteBancaire compteBancaire = new CompteBancaire(
                BigInteger.valueOf(numeroDeCompte),
                BigDecimal.valueOf(solde));
        assertEquals(BigInteger.valueOf(numeroDeCompte), compteBancaire.getNumeroDeCompte());
        assertEquals(BigDecimal.valueOf(solde), compteBancaire.getSolde());
    }

    @ParameterizedTest
    @CsvSource({
            "213213213, -10,101"
    }

    )
    void shouldNotCreateBankAccountForIncorrectValues(int numeroDeCompte, int solde) {
        assertThrows(IllegalArgumentException.class, () ->
                new CompteBancaire(
                        BigInteger.valueOf(numeroDeCompte),
                        BigDecimal.valueOf(solde)));
    }
}
