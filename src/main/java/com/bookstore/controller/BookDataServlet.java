package com.bookstore.controller;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bookstore.DAO.BookDAO;
import com.bookstore.model.Book;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BookDataServlet
 */
@WebServlet("/api/books")
public class BookDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(BookDataServlet.class);
    private BookDAO bookDAO;
    
    public void init() {
        bookDAO = new BookDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            // DataTables parameters
            int draw = Integer.parseInt(request.getParameter("draw"));
            int start = Integer.parseInt(request.getParameter("start"));
            int length = Integer.parseInt(request.getParameter("length"));
            String searchValue = request.getParameter("search[value]");
            
            // Calculate page number (1-based for our DAO)
            int page = (start / length) + 1;
            
            // Get data from database
            List<Book> books;
            long totalRecords;
            long filteredRecords;
            
            if (searchValue != null && !searchValue.isEmpty()) {
                // Search in title, author, and genre
                books = bookDAO.searchBooks(searchValue, page, length);
                filteredRecords = bookDAO.getTotalSearchResults(searchValue);
                totalRecords = bookDAO.getTotalBooks();
            } else {
                books = bookDAO.getBooks(page, length);
                totalRecords = bookDAO.getTotalBooks();
                filteredRecords = totalRecords;
            }
            
            // Build JSON response
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("draw", draw);
            jsonResponse.addProperty("recordsTotal", totalRecords);
            jsonResponse.addProperty("recordsFiltered", filteredRecords);
            
            JsonArray data = new JsonArray();
            for (Book book : books) {
                JsonArray row = new JsonArray();
                row.add(book.getBookId().toString());
                row.add(book.getTitle());
                row.add(book.getAuthor());
                row.add(book.getGenre());
                row.add(book.getPrice().toString());
                
                // Add action buttons
                String actions = "<a href='" + request.getContextPath() + "/books/view?id=" + book.getBookId() + 
                                "' class='btn btn-sm btn-primary'>View</a>";
                
                // Add edit/delete buttons for admin
                if (request.getSession().getAttribute("user") != null && 
                    "admin".equals(((com.bookstore.model.User)request.getSession().getAttribute("user")).getUsername())) {
                    actions += " <a href='" + request.getContextPath() + "/books/edit?id=" + book.getBookId() + 
                              "' class='btn btn-sm btn-secondary'>Edit</a>";
                    actions += " <a href='" + request.getContextPath() + "/books/delete?id=" + book.getBookId() + 
                              "' class='btn btn-sm btn-danger' onclick='return confirm(\"Are you sure?\")'>Delete</a>";
                }
                
                row.add(actions);
                data.add(row);
            }
            
            jsonResponse.add("data", data);
            out.print(jsonResponse.toString());
        } catch (Exception e) {
            logger.error("Error processing DataTables request", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObject error = new JsonObject();
            error.addProperty("error", "An error occurred while processing your request");
            response.getWriter().print(error.toString());
        }
    }
}