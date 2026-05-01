package storeapp.services;

import storeapp.domain.enums.CategoryState;
import storeapp.utils.CategoryFormValidation;

public class CategoryStateSelector {

    private CategoryStateSelector() {
    }

    public static boolean selectCategoryState(String prompt) {
        while (true) {
            System.out.println(prompt);
            System.out.println("1. Activo");
            System.out.println("2. Inactivo");

            int option = CategoryFormValidation.validateInt("Opcion:");

            switch (option) {
                case 1:
                    return CategoryState.ACTIVE.getValue();
                case 2:
                    return CategoryState.INACTIVE.getValue();
                default:
                    System.out.println("❎ Seleccione una opcion valida.");
            }
        }
    }
}

