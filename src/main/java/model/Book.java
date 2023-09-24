package model;


import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Book {

    private int id;

    private String title;

    private String description;

    private BigDecimal price;

    private Date publishDate;

    private Category category;

    private List<BookAuthor> bookAuthors;

    public Book(int id, String title, String description, BigDecimal price, Date publishDate, Category category, List<BookAuthor> bookAuthors) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.publishDate = publishDate;
        this.category = category;
        this.bookAuthors = bookAuthors;
    }
    private boolean deleted = false;

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<BookAuthor> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(List<BookAuthor> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

    public String getAuthors(){
        //[3,2] -> "3, 2"
        return bookAuthors.stream()
                .map(e -> e.getAuthor().getName()).collect(Collectors.joining(", "));
    }

    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted){
        this.deleted = deleted;
    }
}