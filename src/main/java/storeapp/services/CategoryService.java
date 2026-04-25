package storeapp.services;

import storeapp.domain.Category;
import java.util.List;

public interface CategoryService {
    Category createCategory();
    List<Category> getAllCategories();
    void deleteCategory(int id);
}
