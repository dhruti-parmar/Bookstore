package com.bookstore.controller;

import java.io.IOException;
import java.math.BigDecimal;
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
 * Servlet implementation class BookServlet
 */
@WebServlet("/books/*")
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(BookServlet.class);
    private BookDAO bookDAO;
    private static final int BOOKS_PER_PAGE = 6; // Number of books to display per page
    
    public void init() {
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
            action = "/list";
        }
        
        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertBook(request, response);
                    break;
                case "/delete":
                    deleteBook(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateBook(request, response);
                    break;
                case "/view":
                    viewBook(request, response);
                    break;
                case "/datatable":
                    showDataTableList(request, response);
                    break;
                default:
                    listBooks(request, response);
                    break;
            }
        } catch (Exception e) {
            logger.error("Error in BookServlet", e);
            throw new ServletException(e);
        }
    }
    
    private void listBooks(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
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
        
        // Get books for current page
        List<Book> books = bookDAO.getBooks(page, BOOKS_PER_PAGE);
        
        // Get total number of books
        long totalBooks = bookDAO.getTotalBooks();
        
        // Calculate total pages
        int totalPages = (int) Math.ceil((double) totalBooks / BOOKS_PER_PAGE);
        
        // Set request attributes
        request.setAttribute("books", books);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("booksPerPage", BOOKS_PER_PAGE);
        request.setAttribute("totalBooks", totalBooks);
        
        // Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/book/list.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/book/form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book existingBook = bookDAO.getBookById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/book/form.jsp");
        request.setAttribute("book", existingBook);
        dispatcher.forward(request, response);
    }
    
    private void insertBook(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String genre = request.getParameter("genre");
        String description = request.getParameter("description");
        String isbn = request.getParameter("isbn");
        String publisher = request.getParameter("publisher");
        
        Book newBook = new Book(title, author, price, genre, description, isbn, publisher, 1);
        bookDAO.saveBook(newBook);
        response.sendRedirect("list");
    }
    
    private void updateBook(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String genre = request.getParameter("genre");
        String description = request.getParameter("description");
        String isbn = request.getParameter("isbn");
        String publisher = request.getParameter("publisher");
        
        Book book = new Book(title, author, price, genre, description, isbn, publisher, 1);
        book.setBookId(id);
        bookDAO.saveBook(book);
        response.sendRedirect("list");
    }
    
    private void deleteBook(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        bookDAO.deleteBook(id);
        response.sendRedirect("list");
    }
    
    private void viewBook(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book book = bookDAO.getBookWithReviews(id);
        request.setAttribute("book", book);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/book/detail.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showDataTableList(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/book/datatable-list.jsp");
        dispatcher.forward(request, response);
    }
}