package storeapp.view;

import storeapp.services.CategoryService;
import storeapp.utils.CategoryFormValidation;

public class CategoryView {
    private final CategoryService categoryService;

    public CategoryView(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void createCategory() {
        categoryService.createCategory();
        System.out.println("✅ Categoría registrada exitosamente.");
    }

    public void getAllCategories() {
        System.out.println("\n--- Listado de Categorías ---");
        categoryService.getAllCategories().forEach(c ->
                System.out.println("ID: " + c.getIdCategory() + " | Desc: " + c.getDescription() + " | Estado: " + (c.isState() ? "Activo" : "Inactivo"))
        );
    }

    public void deleteCategory() {
        int id = CategoryFormValidation.validateInt("Ingrese el ID de la categoría a eliminar:");
        categoryService.deleteCategory(id);
        System.out.println("🗑️🗑 Proceso de eliminación finalizado.");
    }
}
