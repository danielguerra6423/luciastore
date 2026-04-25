package storeapp.repository;

import storeapp.domain.Category;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private List<Category> categories = new ArrayList<>();

    public Category saveCategory(Category category) {
        categories.add(category);
        return category;
    }

    public List<Category> findAllCategories() {
        return categories;
    }

    public Category findCategoryById(int id) {
        return categories.stream()
                .filter(c -> c.getIdCategory() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean deleteCategory(int id) {
        return categories.removeIf(c -> c.getIdCategory() == id);
    }
}
