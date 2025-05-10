package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public DashboardPage makeTransfer(int amount, String fromCardNumber) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(fromCardNumber);
        transferButton.click();
        return new DashboardPage();
    }

    public void shouldShowError(String expectedText) {
        errorNotification.shouldBe(text(expectedText));
    }
}
