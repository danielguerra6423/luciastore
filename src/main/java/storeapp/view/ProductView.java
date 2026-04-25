package storeapp.view;

import storeapp.services.ProductService;
import storeapp.utils.ProductFormValidation;

import static java.lang.System.*;

public class ProductView {

    private final ProductService productService;
    public ProductView(ProductService productService) {
        this.productService = productService;
    }

    public  void createProduct(){
        productService.createProduct();
    }

    public void getProductById(int id){
        productService.getProductById(id);
    }
    public void getProductById() {
        productService.getProductById(ProductFormValidation.validateInt("Ingrese el ID del producto a buscar"));
    }
    public void getAllProducts() {
        System.out.println("--- Lista de Productos ---");
        productService.getAllProducts().forEach(System.out::println);
    }
    public void updateProduct(){
        productService.updateProduct(ProductFormValidation.validateInt("Ingrese el ID del producto a actualizar"));
    }
    public void deleteProduct() {
        productService.deleteProduct(ProductFormValidation.validateInt("Ingrese el id del producto a eliminar"));
    }
}
