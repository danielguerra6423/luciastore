package storeapp.services;

import storeapp.domain.enums.ProductState;
import storeapp.utils.ProductFormValidation;

import java.util.Scanner;

public class ProductStateSelector {
    Scanner  sc = new Scanner(System.in);
    public  boolean ProductState(){

        System.out.println("Selecione el estado del producto: ");
        System.out.println("1. Disponible \n" +
                "2. Sin Stock \n" +
                "3. Descontinuado");
        int option = ProductFormValidation.validateInt("Opcion");
       switch (option){
           case 1:
               return ProductState.AVAILABLE.getDescription();
           case 2:
               return ProductState.OUT_OF_STOCK.getDescription();
           case 3:
               return ProductState.DISCONTINUED.getDescription();
           default:
               System.out.println("❎ Seleccione una opcion valida");
               return false;
       }


    }


}
