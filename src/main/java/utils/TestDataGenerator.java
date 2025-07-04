package utils;

public class TestDataGenerator {

    public static String generateUniqueUsername() {
        return "amaan_" + System.currentTimeMillis();
    }

    public static String getDefaultPassword() {
        return "Test@123";
    }

    public static String getCardNumber() {
        return "4111111111111111";
    }

    public static String getCurrentMonth() {
        return "12";
    }

    public static String getCurrentYear() {
        return "2025";
    }
}