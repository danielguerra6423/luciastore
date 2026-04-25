package storeapp.services;

import storeapp.domain.Product;

import java.util.List;

public interface ProductService {
        public Product createProduct();
        public Product getProductById(int id);
        public List<Product> getAllProducts();
        public Product updateProduct(int id);
        public void deleteProduct(int id);
}
