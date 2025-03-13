package com.bookstore.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "genre")
    private String genre;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "status")
    private Integer status = 1; // Default status is active (1)

    // Lazy loading for reviews - they will be loaded only when accessed
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    // Constructors
    public Book() {
    }

    public Book(String title, String author, BigDecimal price, String genre, String description,
    		String isbn, String publisher, Integer status) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.genre = genre;
        this.description = description;
        this.isbn = isbn;
        this.publisher = publisher;
        this.status = status;
    }

    // Getters and Setters
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    // Helper method to add a review
    public void addReview(Review review) {
        this.reviews.add(review);
        review.setBook(this);
    }

    // Helper method to remove a review
    public void removeReview(Review review) {
        this.reviews.remove(review);
        review.setBook(null);
    }

    // Calculate average rating
    public double getAverageRating() {
        if (reviews.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Review review : reviews) {
            sum += review.getRating();
        }

        return sum / reviews.size();
    }

    @Override
    public String toString() {
        return "Book [bookId=" + bookId + ", title=" + title + ", author=" + author + ", price=" + price + "]";
    }
}

