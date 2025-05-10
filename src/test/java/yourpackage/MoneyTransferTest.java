package yourpackage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999"); // или ваш актуальный порт
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        // Вход в систему
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin("vasya", "qwerty123");
        var dashboardPage = verificationPage.validVerify("12345");

        // Получение балансов до перевода
        int firstCardBalanceBefore = dashboardPage.getCardBalance(0); // карта 0001
        int secondCardBalanceBefore = dashboardPage.getCardBalance(1); // карта 0002

        int amount = 500;

        // Перевод с карты 0002 на карту 0001
        var transferPage = dashboardPage.selectCardToTransfer(0); // пополняем первую
        dashboardPage = transferPage.makeTransfer("5559 0000 0000 0002", amount);

        // Получение балансов после перевода
        int firstCardBalanceAfter = dashboardPage
