package vision.services;

import vision.exceptions.DatabaseException;
import vision.exceptions.ServiceValidationException;
import vision.models.Category;
import vision.repositories.CategoryRepository;

import java.sql.SQLException;
import java.util.List;

public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllCategories() {
        return dbGetAllCategories();
    }

    public Category findCategoryById(String categoryId) {
        Long id = Long.parseLong(categoryId);
        validateId(id);
        return dbGetCategoryById(id);
    }

    private void validateId(Long id) {
        if (id < 1) {
            throw new ServiceValidationException();
        }
    }



    //DB WRAPPERS

    private List<Category> dbGetAllCategories() {
        try {
            return categoryRepository.getAllCategories();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private Category dbGetCategoryById(Long id) {
        try {
            return categoryRepository.getCategoryById(id);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private Category dbAddCategory(Category category) {
        try {
            return categoryRepository.addCategory(category);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private boolean dbUpdateCategory(Category category) {
        try {
            return categoryRepository.updateCategory(category);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private boolean dbDeleteCategory(Long categoryId) {
        try {
            return categoryRepository.deleteCategory(categoryId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }
}
