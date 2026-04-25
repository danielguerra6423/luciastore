package storeapp.utils;

import storeapp.domain.Category; // 1. Importamos la clase Category
import storeapp.domain.Product;

import java.util.Scanner;

public class ProductFormValidation {
    static Scanner sc = new Scanner(System.in);

    public static void valideteId(Product product) {
        boolean validInput = false;
        while(!validInput) {
            try{
                System.out.println("Ingrese el id del producto");
                int id = sc.nextInt();
                sc.nextLine();
                validInput = true;
                product.setIdProduct(id);
            } catch (Exception e){
                System.out.println("❎ Error al ingresar el id del producto, por favor ingrese un numero entero");
                validInput = false;
                sc.nextLine();
            }
        }
    }

    public static int validateInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " ");
                return Integer.parseInt(sc.nextLine()); // Usar parseInt es más seguro que nextInt
            } catch (NumberFormatException e) {
                System.out.println("❎ Error: Debe ingresar un número entero válido.");
                // No hace falta sc.nextLine() aquí si usas sc.nextLine() arriba
            }
        }
    }

    public static boolean validateBoolean(String prompt) {
        while(true){
            try{
                System.out.println(prompt);
                boolean value = sc.nextBoolean();
                sc.nextLine();
                return value;
            }catch (Exception e){
                System.out.println("❎ Error al ingresar el valor, este debe ser true o false");
                sc.nextLine();
            }
        }
    }

    public static double validateDouble(String prompt) {
        while(true){
            try{
                System.out.println(prompt);
                double value = sc.nextDouble();
                sc.nextLine();
                return value;
            }catch (Exception e){
                System.out.println("❎ Error al ingresar el valor, este debe ser un numero decimal");
                sc.nextLine();
            }
        }
    }

    public static String validateString(String prompt) {
        while(true){
            try{
                System.out.println(prompt);
                String value = sc.nextLine();
                if(value.isEmpty()){
                    throw new Exception("❎ El valor no puede estar vacio");
                }
                return value;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static boolean validateProductState(String prompt) {
        while(true){
            try{
                System.out.println(prompt);
                boolean value = sc.nextBoolean();
                sc.nextLine();
                return value;
            }catch (Exception e){
                System.out.println("❎ Error al ingresar el valor, este debe ser true o false");
                sc.nextLine();
            }
        }
    }

    // 2. CORRECCIÓN: Ahora devuelve un objeto Category y pide el ID
    public static Category validateProductCategory(String prompt) {
        while(true){
            try{
                System.out.println(prompt);
                int idCat = sc.nextInt();
                sc.nextLine();

                // Creamos un objeto temporal con ese ID
                // para que coincida con el tipo que espera el Producto
                Category category = new Category();
                category.setIdCategory(idCat);
                return category;
            }catch (Exception e){
                System.out.println("❎ Error: Ingrese el ID numérico de la categoría");
                sc.nextLine();
            }
        }
    }

    public static void validateProductForm(Product product) {
        valideteId(product);
        String description = validateString("Ingrese la descripcion del producto");
        product.setDescription(description);
        double price = validateDouble("Ingrese el precio del producto");
        product.setPrice(price);
        int stock = validateInt("Ingrese el stock del producto");
        product.setStock(stock);
        boolean state = validateProductState("Ingrese el estado del producto (true/false)");
        product.setState(state);

        // 3. Ahora esto ya no tirará error porque ambos son tipo Category
        Category category = validateProductCategory("Ingrese el ID de la categoría del producto");
        product.setCategory(category);
    }
}