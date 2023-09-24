package service;

import model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private static List<Category> categories;

    static {
        categories = new ArrayList<>();
        categories.add(new Category(1, "Keo"));
        categories.add(new Category(2, "Banh"));
    }
    public List<Category> getCategories(){
        return categories;
    }

    public Category getCategory(int id){
        return categories.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }
}
