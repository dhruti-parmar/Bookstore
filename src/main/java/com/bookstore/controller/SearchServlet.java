package com.bookstore.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bookstore.DAO.BookDAO;
import com.bookstore.model.Book;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(SearchServlet.class);
    private BookDAO bookDAO;
    private static final int BOOKS_PER_PAGE = 6; // Number of books to display per page
    
    public void init() {
        bookDAO = new BookDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String searchType = request.getParameter("type");
        String searchTerm = request.getParameter("term");
        
        // Get current page from request parameter
        int page = 1;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                logger.warn("Invalid page number: {}", request.getParameter("page"));
            }
        }
        
        List<Book> searchResults = null;
        long totalBooks = 0;
        
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            if ("title".equals(searchType)) {
                searchResults = bookDAO.searchBooksByTitle(searchTerm, page, BOOKS_PER_PAGE);
                totalBooks = bookDAO.getTotalBooksByTitle(searchTerm);
            } else if ("author".equals(searchType)) {
                searchResults = bookDAO.searchBooksByAuthor(searchTerm, page, BOOKS_PER_PAGE);
                totalBooks = bookDAO.getTotalBooksByAuthor(searchTerm);
            } else if ("genre".equals(searchType)) {
                searchResults = bookDAO.searchBooksByGenre(searchTerm, page, BOOKS_PER_PAGE);
                totalBooks = bookDAO.getTotalBooksByGenre(searchTerm);
            } else {
                // Default to title search
                searchResults = bookDAO.searchBooksByTitle(searchTerm, page, BOOKS_PER_PAGE);
                totalBooks = bookDAO.getTotalBooksByTitle(searchTerm);
            }
        } else {
            // If no search term, show all books
            searchResults = bookDAO.getBooks(page, BOOKS_PER_PAGE);
            totalBooks = bookDAO.getTotalBooks();
        }
        
        // Calculate total pages
        int totalPages = (int) Math.ceil((double) totalBooks / BOOKS_PER_PAGE);
        
        // Set request attributes
        request.setAttribute("books", searchResults);
        request.setAttribute("searchTerm", searchTerm);
        request.setAttribute("searchType", searchType);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("booksPerPage", BOOKS_PER_PAGE);
        request.setAttribute("totalBooks", totalBooks);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/book/list.jsp");
        dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}