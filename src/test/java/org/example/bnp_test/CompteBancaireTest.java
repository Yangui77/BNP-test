package org.example.bnp_test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.example.bnp_test.CompteBancaire.DecimalErrorMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CompteBancaireTest {

    private final BigInteger defaultAccountNumber = BigInteger.valueOf(213213213);
    private final BigDecimal defaultBalance = BigDecimal.valueOf(100);

    @ParameterizedTest
    @CsvSource({
            "1, 2",
            "213213213, 0",
            "213213213, -10",
            "213213213, -10.10",
            "212131221, 20.50"
    }

    )
    void shouldCreateBankAccountWithCorrectValues(int numeroDeCompte, BigDecimal solde) {
        CompteBancaire compteBancaire = new CompteBancaire(
                BigInteger.valueOf(numeroDeCompte),
                solde);
        assertEquals(BigInteger.valueOf(numeroDeCompte), compteBancaire.getNumeroDeCompte());
        assertEquals(solde, compteBancaire.getSolde());
    }

    @ParameterizedTest
    @CsvSource({
            "213213213, -10.101",
            "213213213, -10.1011112",
            "213213213, -10.10212",
            "213213213, -10.101111",
            "213213213, 10.101111",
            "213213213, 10.101115",
            "213213213, 50.101112",

    }

    )
    void shouldNotCreateBankAccountForIncorrectValues(int numeroDeCompte, BigDecimal solde) {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new CompteBancaire(
                        BigInteger.valueOf(numeroDeCompte),
                        solde));
        assertEquals(DecimalErrorMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"10, 110"})
    void shouldAddDeposit(BigDecimal amount, BigDecimal expected) {
        CompteBancaire compteBancaire = new CompteBancaire(
                defaultAccountNumber,
                defaultBalance);
        compteBancaire.depot(amount);
        assertEquals(expected, compteBancaire.getSolde());
    }


}
