package storeapp.services;

import storeapp.domain.enums.ProductState;
import storeapp.utils.ProductFormValidation;

public class ProductStateSelector {

    private ProductStateSelector() {
    }

    public static boolean selectProductState(String prompt) {
        while (true) {
            System.out.println(prompt);
            System.out.println("1. Disponible");
            System.out.println("2. Sin Stock");
            System.out.println("3. Descontinuado");

            int option = ProductFormValidation.validateInt("Opcion:");
            switch (option) {
                case 1:
                    return ProductState.AVAILABLE.getDescription();
                case 2:
                    return ProductState.OUT_OF_STOCK.getDescription();
                case 3:
                    return ProductState.DISCONTINUED.getDescription();
                default:
                    System.out.println("❎ Seleccione una opcion valida");
            }
        }
    }
}
