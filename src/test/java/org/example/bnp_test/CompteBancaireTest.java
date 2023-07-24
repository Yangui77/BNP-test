package org.example.bnp_test;

import org.example.bnp_test.exception.SoldeInsuffisantException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.example.bnp_test.CompteBancaire.DecimalErrorMessage;
import static org.example.bnp_test.CompteBancaire.NegativeAmountErrorMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CompteBancaireTest {

    private final BigInteger defaultAccountNumber = BigInteger.valueOf(213213213);
    private final BigDecimal defaultBalance = BigDecimal.valueOf(100);

    private final BigInteger transferPayeeAccountNumber = BigInteger.valueOf(111111111);

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
    @CsvSource({
            "10, 110",
            "20, 120",
            "20.10, 120.10"})
    void shouldAddBalance(BigDecimal amount, BigDecimal expected) {
        CompteBancaire compteBancaire = new CompteBancaire(
                defaultAccountNumber,
                defaultBalance);
        compteBancaire.depot(amount);
        assertEquals(expected, compteBancaire.getSolde());
    }

    @ParameterizedTest
    @CsvSource({
            "10, -90",
            "20, -80",
            "20.10, -79.90"})
    void shouldAddBalanceFromNegativeBalance(BigDecimal amount, BigDecimal expected) {
        CompteBancaire compteBancaire = new CompteBancaire(
                defaultAccountNumber,
                defaultBalance.negate());
        compteBancaire.depot(amount);
        assertEquals(expected, compteBancaire.getSolde());
    }

    @ParameterizedTest
    @CsvSource({
            "10.1212",
            "-10.1231213"})
    void shouldThrowIllegalAgumentExceptionIfAmountHasMoreThanTwoDecimals(BigDecimal amount) {
        CompteBancaire compteBancaire = new CompteBancaire(
                defaultAccountNumber, defaultBalance);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> compteBancaire.depot(amount));
        assertEquals(DecimalErrorMessage, exception.getMessage());
        assertEquals(compteBancaire.getSolde(), defaultBalance);
    }

    @ParameterizedTest
    @CsvSource({
            "-10.12",
            "-20.14",
            "-40"})
    void shouldThrowIllegalAgumentExceptionIfAmountIsNegative(BigDecimal amount) {
        CompteBancaire compteBancaire = new CompteBancaire(
                defaultAccountNumber, defaultBalance);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> compteBancaire.depot(amount));
        assertEquals(NegativeAmountErrorMessage, exception.getMessage());
        assertEquals(defaultBalance, compteBancaire.getSolde());
    }

    @ParameterizedTest
    @CsvSource({
            "100, 0",
            "20, 80",
            "20.10, 79.90"})
    void shouldWithdraw(BigDecimal amount, BigDecimal expected) throws SoldeInsuffisantException {
        CompteBancaire compteBancaire = new CompteBancaire(
                defaultAccountNumber, defaultBalance);
        compteBancaire.retrait(amount);
        assertEquals(expected, compteBancaire.getSolde());
    }

    @ParameterizedTest
    @CsvSource({
            "-100",
            "-200"})
    void shouldThrowIllegalArugmentExceptionWithNegativeAmount(BigDecimal amount) {
        CompteBancaire compteBancaire = new CompteBancaire(
                defaultAccountNumber, defaultBalance);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            compteBancaire.retrait(amount);
        });
        assertEquals(illegalArgumentException.getMessage(), NegativeAmountErrorMessage);
        assertEquals(defaultBalance, compteBancaire.getSolde());
    }

    @ParameterizedTest
    @CsvSource({
            "10.1212",
            "-10.1231213"})
    void shouldThrowIllegalAgumentExceptionIfAmountHasMoreThanTwoDecimalsForWithdrawal(BigDecimal amount) {
        CompteBancaire compteBancaire = new CompteBancaire(
                defaultAccountNumber, defaultBalance);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> compteBancaire.retrait(amount));
        assertEquals(DecimalErrorMessage, exception.getMessage());
        assertEquals(compteBancaire.getSolde(), defaultBalance);
    }

    @ParameterizedTest
    @CsvSource({
            "110",
            "120"})
    void shouldThrowSoldeInsuffisantExceptionIfAmountIsHigherThantBalance(BigDecimal amount) {
        CompteBancaire compteBancaire = new CompteBancaire(
                defaultAccountNumber, defaultBalance);
        assertThrows(SoldeInsuffisantException.class, () -> {
            compteBancaire.retrait(amount);
        });
        assertEquals(defaultBalance, compteBancaire.getSolde());
    }

    @ParameterizedTest
    @CsvSource({
            "50, 50, 150",
            "30, 70, 130"})
    void shouldTransferBalance(BigDecimal amount, BigDecimal expectedSolde1, BigDecimal expectedSolde2) throws SoldeInsuffisantException {
        CompteBancaire compteBancaire = new CompteBancaire(
                defaultAccountNumber, defaultBalance);
        CompteBancaire compteBancaireDestination = new CompteBancaire(
                transferPayeeAccountNumber, defaultBalance);
        compteBancaire.transferer(
                compteBancaireDestination, amount
        );
        assertEquals(expectedSolde1, compteBancaire.getSolde());
        assertEquals(expectedSolde2, compteBancaireDestination.getSolde());
    }

    @ParameterizedTest
    @CsvSource({
            "-100",
            "-200"})
    void shouldThrowIllegalArugmentExceptionWithNegativeAmountForTransfer(BigDecimal amount) {
        CompteBancaire compteBancaire = new CompteBancaire(
                defaultAccountNumber, defaultBalance);
        CompteBancaire compteBancaireDestination = new CompteBancaire(
                transferPayeeAccountNumber, defaultBalance);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            compteBancaire.transferer(compteBancaireDestination, amount);
        });
        assertEquals(illegalArgumentException.getMessage(), NegativeAmountErrorMessage);
        assertEquals(defaultBalance, compteBancaire.getSolde());
        assertEquals(defaultBalance, compteBancaireDestination.getSolde());
    }

    @ParameterizedTest
    @CsvSource({
            "10.1212",
            "-10.1231213"})
    void shouldThrowIllegalAgumentExceptionIfAmountHasMoreThanTwoDecimalsForWithdrawalForTransfer(BigDecimal amount) {
        CompteBancaire compteBancaire = new CompteBancaire(
                defaultAccountNumber, defaultBalance);
        CompteBancaire compteBancaireDestination = new CompteBancaire(
                transferPayeeAccountNumber, defaultBalance);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> compteBancaire.transferer(compteBancaireDestination, amount));
        assertEquals(DecimalErrorMessage, exception.getMessage());
        assertEquals(compteBancaire.getSolde(), defaultBalance);
        assertEquals(compteBancaireDestination.getSolde(), defaultBalance);
    }

    @ParameterizedTest
    @CsvSource({
            "110",
            "120"})
    void shouldThrowSoldeInsuffisantExceptionIfAmountIsHigherThantBalanceForTransfer(BigDecimal amount) {
        CompteBancaire compteBancaire = new CompteBancaire(
                defaultAccountNumber, defaultBalance);
        CompteBancaire compteBancaireDestination = new CompteBancaire(
                defaultAccountNumber, defaultBalance);
        assertThrows(SoldeInsuffisantException.class, () -> {
            compteBancaire.transferer(compteBancaireDestination, amount);
        });
        assertEquals(defaultBalance, compteBancaire.getSolde());
        assertEquals(defaultBalance, compteBancaireDestination.getSolde());
    }
}
