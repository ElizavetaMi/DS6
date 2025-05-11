package ru.netology.data;

import lombok.Value;

import java.util.List;

public class DataHelper {

    // Данные для авторизации
    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    // Код подтверждения
    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    // Номера карт
    public static String getCardNumber(int cardIndex) {
        if (cardIndex == 0) return "5559 0000 0000 0001"; // Первая карта
        else return "5559 0000 0000 0002"; // Вторая карта
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    @Value
    public static class CardInfo {
        String number;
        String id;
    }

    public static List<CardInfo> getCards() {
        return List.of(
                new CardInfo("5559 0000 0000 0001", "01"),
                new CardInfo("5559 0000 0000 0002", "02")
        );
    }

    public static CardInfo getCardByIndex(int index) {
        return getCards().get(index);
    }
}