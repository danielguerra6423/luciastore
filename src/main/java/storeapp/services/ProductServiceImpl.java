package storeapp.services;

import storeapp.domain.Category;
import storeapp.domain.Product;
import storeapp.repository.CategoryRepository;
import storeapp.repository.ProductRepository;
import storeapp.utils.ProductFormValidation;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct() {
        Product product = new Product();

        // 1. Validar ID Único (Regla de negocio: Unicidad)
        while (true) {
            int id = ProductFormValidation.validateInt("Ingrese el ID del producto:");
            if (productRepository.findProductById(id) == null) {
                product.setIdProduct(id);
                break;
            }
            System.out.println("❎ Error: Ya existe un producto con el ID " + id);
        }

        // 2. Información Obligatoria (Descripción no vacía)
        product.setDescription(ProductFormValidation.validateString("Ingrese la descripción del producto:"));

        // 3. Validar Precio (Debe ser mayor a 0)
        double price;
        do {
            price = ProductFormValidation.validateDouble("Ingrese el precio del producto (mayor a 0):");
            if (price <= 0) System.out.println("❎ El precio debe ser un valor positivo.");
        } while (price <= 0);
        product.setPrice(price);

        // 4. Validar Stock (No puede ser negativo)
        int stock;
        do {
            stock = ProductFormValidation.validateInt("Ingrese el stock del producto (0 o más):");
            if (stock < 0) System.out.println("❎ El stock no puede ser negativo.");
        } while (stock < 0);
        product.setStock(stock);

        product.setState(ProductStateSelector.selectProductState("Seleccione el estado del producto:"));

        // 5. VINCULACIÓN OBLIGATORIA (Cumple requisito: Categoría debe existir)
        while (true) {
            int idCat = ProductFormValidation.validateInt("Ingrese el ID de la categoría para vincular:");
            Category categoriaReal = categoryRepository.findCategoryById(idCat);

            if (categoriaReal != null) {
                product.setCategory(categoriaReal);
                System.out.println("✅ Categoría '" + categoriaReal.getDescription() + "' vinculada correctamente.");
                break;
            } else {
                System.out.println("❎ Error: La categoría con ID " + idCat + " no existe. Debe crearla primero.");
                // Opcional: mostrar categorías disponibles para ayudar al admin
                if (categoryRepository.findAllCategories().isEmpty()) {
                    System.out.println("⚠️ No hay categorías registradas en el sistema.");
                }
            }
        }

        System.out.println("✅ Producto guardado exitosamente.");
        return productRepository.saveProduct(product);
    }

    @Override
    public Product getProductById(int id) {
        Product product = productRepository.findProductById(id);
        if (product != null) {
            System.out.println(product);
        } else {
            System.out.println("❎ Producto no encontrado con el ID: " + id);
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAllProducts();
    }

    @Override
    public Product updateProduct(int id) {
        Product product = productRepository.findProductById(id);

        if (product != null) {
            System.out.println("--- Menú Actualizar Producto ---");
            System.out.println("1. ID\n2. Descripcion\n3. Precio\n4. Stock\n5. Estado\n6. Categoria");

            int option = ProductFormValidation.validateInt("Seleccione una opción:");

            switch (option) {
                case 1:
                    product.setIdProduct(ProductFormValidation.validateInt("Ingrese nuevo ID:"));
                    break;
                case 2:
                    product.setDescription(ProductFormValidation.validateString("Ingrese nueva descripción:"));
                    break;
                case 3:
                    product.setPrice(ProductFormValidation.validateDouble("Ingrese nuevo precio:"));
                    break;
                case 4:
                    product.setStock(ProductFormValidation.validateInt("Ingrese nuevo stock:"));
                    break;
                case 5:
                    product.setState(ProductStateSelector.selectProductState("Seleccione el nuevo estado del producto:"));
                    break;
                case 6:
                    // Validación también en la actualización
                    while(true) {
                        int idNewCat = ProductFormValidation.validateInt("Ingrese el ID de la nueva categoría:");
                        Category catRealUpdate = categoryRepository.findCategoryById(idNewCat);
                        if(catRealUpdate != null) {
                            product.setCategory(catRealUpdate);
                            break;
                        }
                        System.out.println("❎ Categoría no encontrada. Intente de nuevo.");
                    }
                    break;
                default:
                    System.out.println("❎ Opción no válida.");
                    break;
            }
            productRepository.updateProduct(id, product);
        } else {
            System.out.println("❎ Producto no encontrado con el ID: " + id);
        }

        return product;
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteProduct(id);
        System.out.println("🗑️ Producto eliminado correctamente.");
    }
}