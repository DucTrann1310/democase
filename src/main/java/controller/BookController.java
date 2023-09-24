package controller;

import model.Book;
import model.Product;
import service.AuthorService;
import service.BookService;
import service.CategoryService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "bookController", urlPatterns = "/book")
public class BookController extends HttpServlet {
    private BookService bookService;
    private CategoryService categoryService;
    private AuthorService authorService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCreate(req, resp);
                break;
            case "edit":
                showEdit(req,resp);
                break;
            case "delete":
                showDelete(req,resp);
                break;
            case "search":
                showSearch(req,resp);
                break;
            case "restore":
                showRestore(req,resp);
                break;
            default:
                showList(req, resp);
        }
    }

    private void showRestore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("books", bookService.getBooks(true));
        req.setAttribute("message",req.getParameter("message"));
        req.getRequestDispatcher("/book/restore.jsp").forward(req,resp);
    }

    private void showDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String deletedBooksString = req.getParameter("deletedBooks");
        if (deletedBooksString != null && !deletedBooksString.isEmpty()) {
            String[] deletedBooks = deletedBooksString.split(",");
            int[] bookIds = new int[deletedBooks.length];
            for (int i = 0; i < deletedBooks.length; i++) {
                bookIds[i] = Integer.parseInt(deletedBooks[i]);
            }
            for (int id : bookIds) {
                bookService.removeBook(id);
            }
            resp.sendRedirect("/book?message=DeletedSuccessfully");
        } else {
            resp.sendRedirect("/book?message=NoBooksAreDeleted");
        }

    }

    private void showSearch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = req.getParameter("result").trim().toLowerCase();
        if(result != "" || !result.trim().isEmpty()){
            List<Book> searchBooks = bookService.findByName(result);
            if(searchBooks != null){
                req.setAttribute("books",searchBooks);
                req.getRequestDispatcher("/book/index.jsp").forward(req,resp);
            }
            else {
                List<Book> books = bookService.getBooks(false);
                req.setAttribute("books",books);
                req.getRequestDispatcher("/book/index.jsp").forward(req,resp);
            }
        }else {
            req.setAttribute("books", bookService.getBooks(false));
            req.setAttribute("message", req.getParameter("message"));
            req.getRequestDispatcher("book/index.jsp").forward(req, resp);
        }
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp){
        int id = Integer.parseInt(req.getParameter("id"));
        Book book = bookService.findById(id);
        RequestDispatcher dispatcher;


        if(book == null){
            dispatcher = req.getRequestDispatcher("/book.error-404.jsp");
        }else {
            req.setAttribute("categories", categoryService.getCategories());
            req.setAttribute("authors", authorService.findAll());
            req.setAttribute("book", book);
            dispatcher = req.getRequestDispatcher("/book/edit.jsp");
        }try {
            dispatcher.forward(req,resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("books", bookService.getBooks(false));
        req.setAttribute("message", req.getParameter("message"));
        req.getRequestDispatcher("book/index.jsp").forward(req, resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categories", categoryService.getCategories());
        req.setAttribute("authors", authorService.findAll());
        req.getRequestDispatcher("book/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                create(req, resp);
                break;
            case "edit":
                edit(req,resp);
                break;
            case "restore":
                restore(req,resp);
                break;
        }
    }

    private void restore(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] restoredBooksIds = req.getParameterValues("restoredBook");
        if(restoredBooksIds != null && restoredBooksIds.length > 0){
            for(int i = 0;i < restoredBooksIds.length;i++){
                bookService.restoreBook(Integer.parseInt(restoredBooksIds[i]));
            }
            resp.sendRedirect("/book?message=Restored");
        }else {
            resp.sendRedirect("/book?message=NoProductsSelected");
        }
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        bookService.update(req,id);
        resp.sendRedirect("/book?message=Updated");
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        bookService.create(req);
        resp.sendRedirect("/book?message=Created");
    }

    @Override
    public void init() {
        bookService = new BookService();
        categoryService = new CategoryService();
        authorService = new AuthorService();
    }
}
