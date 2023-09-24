package controller;

import model.Category;
import model.EGender;
import model.Product;
import model.Students;
import service.CategoryService;
import service.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "productController", urlPatterns = "/product")
public class ProductController extends HttpServlet {
    private ProductService productService;
    private CategoryService categoryService;
    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        categoryService = new CategoryService();

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            case "create":
                showCreate(req,resp);
                break;
            case "delete":
                delete(req,resp);
                break;
            case "restore":
                showRestore(req,resp);
                break;
            case "edit":
                showEdit(req,resp);
                break;
            case "search":
                showSearch(req,resp);
                break;
            default:
                showList(req,resp);
        }
    }

    private void showSearch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = req.getParameter("result").trim().toLowerCase();

        if(result != "" || !result.trim().isEmpty()){
            List<Product> searchProducts = productService.findByName(result);
            if(searchProducts != null){
                req.setAttribute("products",searchProducts);
                req.getRequestDispatcher("/product/index.jsp").forward(req,resp);
            }
            else {
                List<Product> products = productService.getProducts(false);
                req.setAttribute("products",products);
                req.getRequestDispatcher("/product/index.jsp").forward(req,resp);
            }
        }else {
            req.setAttribute("products", productService.getProducts(false));
            req.setAttribute("message", req.getParameter("message"));
            req.getRequestDispatcher("product/index.jsp").forward(req, resp);
        }

    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productService.findById(id);
        RequestDispatcher dispatcher;
        if (product == null) {
            dispatcher = req.getRequestDispatcher("/product/error-404.jsp");
        } else {
            req.setAttribute("categories", categoryService.getCategories());
            req.setAttribute("product", product);
            dispatcher = req.getRequestDispatcher("/product/edit.jsp");
            //
        }
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showRestore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", productService.getProducts(true));
        req.setAttribute("message",req.getParameter("message"));
        req.getRequestDispatcher("/product/restore.jsp").forward(req,resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        productService.removeProduct(id);
        resp.sendRedirect("/product?message=Deleted");
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageString = req.getParameter("page");
        if(pageString == null)

        req.setAttribute("products", productService.getProducts(false));
        req.setAttribute("message", req.getParameter("message"));
        req.getRequestDispatcher("product/index.jsp").forward(req, resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categories", categoryService.getCategories());
        req.getRequestDispatcher("product/create.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            case "create":
                create(req,resp);
                break;
            case "edit":
                update(req,resp);
                break;
            case "restore":
                restore(req,resp);
                break;
        }
    }

    private void restore(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] restoredProductIds = req.getParameterValues("restoredProduct");
        if(restoredProductIds != null && restoredProductIds.length > 0){
            for(int i = 0;i < restoredProductIds.length;i++){
                productService.restoreProduct(Integer.parseInt(restoredProductIds[i]));
            }
            resp.sendRedirect("/product?message=Restored");
        }else {
            resp.sendRedirect("/product?message=NoProductsSelected");
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productService.findById(id);
        String name = req.getParameter("name").trim();
        String strPrice = req.getParameter("price").trim();
        String description = req.getParameter("description").trim();
        String idCategory = req.getParameter("category");

        if(checkEmpty(name,strPrice,description)){
            req.setAttribute("product",product);
            req.setAttribute("categories", categoryService.getCategories());
            req.setAttribute("message","Fields cannot be empty");
            req.getRequestDispatcher("/product/edit.jsp").forward(req, resp);
        }else {
            BigDecimal price = new BigDecimal(strPrice);
            Category category = categoryService.getCategory(Integer.parseInt(idCategory));
            productService.updateProduct(id, name, price, description, category);
            resp.sendRedirect("/product?message=Updated");
        }
    }
    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name").trim();
        String strPrice = req.getParameter("price").trim();
        String description = req.getParameter("description").trim();
        String idCategory = req.getParameter("category");

        if(checkEmpty(name,strPrice,description)){
            req.setAttribute("categories", categoryService.getCategories());
            req.setAttribute("message","Fields cannot be empty");
            req.getRequestDispatcher("/product/create.jsp").forward(req, resp);
        }else {
            BigDecimal price = new BigDecimal(strPrice.trim());
            Category category = categoryService.getCategory(Integer.parseInt(idCategory));
            productService.create(new Product(name, price,description,category));
            resp.sendRedirect("/product?message=Created");
        }
    }
    private boolean checkEmpty(String name, String price, String description    ){
        if(name.isEmpty() || name == null || name.trim().isEmpty()
                || price.isEmpty() || price == null || price.trim().isEmpty()
                || description.isEmpty() || description == null || description.trim().isEmpty()){
            return true;
        }
        return false;
    }
}
