package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode.getCode());

        var firstCardBalance = dashboardPage.getCardBalance(0);
        var secondCardBalance = dashboardPage.getCardBalance(1);

        // Переводим 10% от остатка на второй карте, но минимум 1 рубль
        int transferAmount = Math.max(secondCardBalance / 10, 1);

        var transferPage = dashboardPage.chooseCardToReplenish(0);
        dashboardPage = transferPage.makeTransfer(transferAmount, DataHelper.getCardByIndex(1).getNumber());

        var expectedFirstCardBalance = firstCardBalance + transferAmount;
        var expectedSecondCardBalance = secondCardBalance - transferAmount;

        assertEquals(expectedFirstCardBalance, dashboardPage.getCardBalance(0));
        assertEquals(expectedSecondCardBalance, dashboardPage.getCardBalance(1));
    }


    @Test
    void shouldNotAllowTransferMoreThanAvailable() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode.getCode());

        var balanceFromCard = dashboardPage.getCardBalance(1);
        int transferAmount = balanceFromCard + 1; // на 1 рубль больше, чем есть

        var transferPage = dashboardPage.chooseCardToReplenish(0);
        transferPage.makeTransfer(transferAmount, DataHelper.getCardByIndex(1).getNumber());

        transferPage.shouldShowError("Ошибка! Недостаточно средств на карте");
    }

}

