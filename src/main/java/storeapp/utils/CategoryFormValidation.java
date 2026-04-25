package storeapp.utils;

import java.util.Scanner;

public class CategoryFormValidation {
    private static final Scanner sc = new Scanner(System.in);


    public static int validateInt(String message) {
        while (true) {
            try {
                System.out.println(message);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❎ Error: Debe ingresar un número entero válido.");
            }
        }
    }


    public static String validateString(String message) {
        while (true) {
            System.out.println(message);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("❎ Error: Este campo no puede estar vacío.");
        }
    }


    public static boolean validateBoolean(String message) {
        while (true) {
            System.out.println(message + " (true/false):");
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("true")) return true;
            if (input.equals("false")) return false;
            System.out.println("❎ Error: Debe ingresar 'true' o 'false'.");
        }
    }
}