package service;

import dao.ProductDao;
import model.Category;
import model.Product;
import model.Students;
import service.dto.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private static List<Product> products = new ArrayList<>();

    private static int idCurrent;
    private final ProductDao productDao;
    public ProductService(){
        productDao = new ProductDao();
    }

    public void create(Product product){

    }

    public List<Product> getProducts(boolean deleted){
//        return productDao.findAll(page);
        return products.stream()
                .filter(s -> s.isDeleted() == deleted)
                .collect(Collectors.toList());
    }
    public void removeProduct(int id) {
        Product product = findById(id);
        product.setDeleted(true);
    }

    public void restoreProduct(int id){
        Product product = findById(id);
        product.setDeleted(false);
    }
    public Product findById(int id) {
        return products.stream()
                .filter(s -> s.getId() == id)
                .findFirst().orElse(null);
    }

    public void updateProduct(int id, String name, BigDecimal price, String description, Category category) {
        products.stream()
                .filter(s -> s.getId() == id)
                .collect(Collectors.toList())
                .forEach(s -> {s.setName(name); s.setPrice(price); s.setDescription(description); s.setCategory(category);});
    }

    public List<Product> findByName(String productName) {
        return products.stream()
                .filter(s -> s.getName().toLowerCase().contains(productName) && s.isDeleted() == false)
                .collect(Collectors.toList());
    }
}
