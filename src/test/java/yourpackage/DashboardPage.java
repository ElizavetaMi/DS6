package yourpackage;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public int getCardBalance(int index) {
        var text = cards.get(index).text();
        return extractBalance(text);
    }

    public TransferPage selectCardToTransfer(int index) {
        cards.get(index).$("button").click(); // кнопка "Пополнить"
        return new TransferPage();
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var end = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), end);
        return Integer.parseInt(value.trim());
    }
}

