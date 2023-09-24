package service;

import model.BookAuthor;

import java.util.ArrayList;
import java.util.List;

public class BookAuthorService {
    private static List<BookAuthor> bookAuthors;
    private static int idCurrent;

    static {
        bookAuthors = new ArrayList<>();
    }

    public BookAuthor create(BookAuthor bookAuthor){
        bookAuthor.setId(++idCurrent);
        bookAuthors.add(bookAuthor);
        return bookAuthor;
    }

    public void delete(int id){
        bookAuthors = bookAuthors.stream().filter(e -> e.getId() == id).toList();
    }
}
