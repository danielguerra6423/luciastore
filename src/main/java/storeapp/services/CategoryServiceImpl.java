package storeapp.services;

import storeapp.domain.Category;
import storeapp.repository.CategoryRepository;
import storeapp.utils.ProductFormValidation; // Importamos el validador

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory() {
        Category category = new Category();

        // 1. Validar ID
        while (true) {
            int id = ProductFormValidation.validateInt("Ingrese el ID de la categoría:");
            if (categoryRepository.findCategoryById(id) == null) {
                category.setIdCategory(id);
                break;
            }
            System.out.println("❌ Error: Ya existe una categoría con el ID " + id);
        }

        // 2. Validar Descripción (No vacía) y Unicidad de Nombre
        while (true) {
            String desc = ProductFormValidation.validateString("Ingrese la descripción de la categoría:");

            // Comprobar si el nombre ya existe en la lista
            boolean nameExists = false;
            for (Category existing : categoryRepository.findAllCategories()) {
                if (existing.getDescription().equalsIgnoreCase(desc)) {
                    nameExists = true;
                    break;
                }
            }

            if (!nameExists) {
                category.setDescription(desc);
                break;
            }
            System.out.println("❌ Error: Ya existe una categoría llamada '" + desc + "'.");
        }

        // 3. Validar Estado
        category.setState(ProductFormValidation.validateProductState("Ingrese el estado (true/false):"));

        System.out.println("✅ Categoría creada exitosamente.");
        return categoryRepository.saveCategory(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAllCategories();
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteCategory(id);
    }
}