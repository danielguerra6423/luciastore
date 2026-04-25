package storeapp.repository;

import storeapp.domain.Product;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductRepository {
    // CORRECCIÓN 1: Se agrega 'null' como último parámetro porque el constructor ahora pide Category
    private List<Product> products = new ArrayList<>(Arrays.asList(
            new Product(1, "Producto 1", 10.0, 100, true, null),
            new Product(2, "Producto 2", 20.0, 200, true, null)
    ));

    public Product saveProduct(Product product) {
        products.add(product);
        return product;
    }

    public List<Product> findAllProducts() {
        // Aprovechamos el toString() que creamos en la clase Product
        for (Product product : products) {
            System.out.println(product);
        }
        return products;
    }

    public Product findProductById(int id) {
        for (Product product : products) {
            if (product.getIdProduct() == id) {
                return product; // Retorna el producto encontrado
            }
        }
        return null; // Si termina el ciclo y no lo encuentra
    }

    public Product updateProduct(int id, Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getIdProduct() == id) {
                products.set(i, updatedProduct);
                return updatedProduct;
            }
        }
        return null;
    }

    // CORRECCIÓN 2: Error de ConcurrentModificationException y remove por índice
    public boolean deleteProduct(int id) {
        // Usamos removeIf para evitar errores al modificar la lista mientras se recorre
        return products.removeIf(product -> product.getIdProduct() == id);
    }
}