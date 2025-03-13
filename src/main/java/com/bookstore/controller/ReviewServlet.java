package com.bookstore.controller;

import java.io.IOException;

import com.bookstore.DAO.BookDAO;
import com.bookstore.DAO.ReviewDAO;
import com.bookstore.model.Book;
import com.bookstore.model.Review;
import com.bookstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ReviewServlet
 */
@WebServlet("/review/*")
public class ReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReviewDAO reviewDAO;
    private BookDAO bookDAO;
    
    public void init() {
        reviewDAO = new ReviewDAO();
        bookDAO = new BookDAO();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        
        if (action == null) {
            action = "/add";
        }
        
        switch (action) {
            case "/add":
                addReview(request, response);
                break;
            case "/delete":
                deleteReview(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/books/list");
                break;
        }
    }
    
    private void addReview(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        
        // Get parameters
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");
        
        // Validate rating
        if (rating < 1 || rating > 5) {
            rating = 5; // Default to 5 if invalid
        }
        
        // Get book
        Book book = bookDAO.getBookById(bookId);
        
        if (book != null) {
            // Create and save review
            Review review = new Review(book, user, rating, comment);
            reviewDAO.saveReview(review);
        }
        
        // Redirect back to book detail page
        response.sendRedirect(request.getContextPath() + "/books/view?id=" + bookId);
    }
    
    private void deleteReview(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        
        // Get parameters
        int reviewId = Integer.parseInt(request.getParameter("id"));
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        
        // Get review
        Review review = reviewDAO.getReviewById(reviewId);
        
        // Check if review exists and belongs to the current user
        if (review != null && review.getUser().getUserId().equals(user.getUserId())) {
            reviewDAO.deleteReview(reviewId);
        }
        
        // Redirect back to book detail page
        response.sendRedirect(request.getContextPath() + "/books/view?id=" + bookId);
    }
}