package com.bookstore.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bookstore.DAO.UserDAO;
import com.bookstore.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action == null) {
            action = "/login";
        }

        try {
            switch (action) {
                case "/register":
                    showRegisterForm(request, response);
                    break;
                case "/registerProcess":
                    registerUser(request, response);
                    break;
                case "/login":
                    showLoginForm(request, response);
                    break;
                case "/loginProcess":
                    loginUser(request, response);
                    break;
                case "/logout":
                    logoutUser(request, response);
                    break;
                default:
                    showLoginForm(request, response);
                    break;
            }
        } catch (Exception e) {
        	logger.error("Error in UserServlet", e);
            throw new ServletException(e);
        }
    }

    private void showRegisterForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	logger.info("Showing register form");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/register.jsp");
        dispatcher.forward(request, response);
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        	logger.info("Registering user");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");

            // Validate input
            if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {

                request.setAttribute("errorMessage", "All fields are required");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/register.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Check if username or email already exists
            if (userDAO.isUsernameExists(username)) {
                request.setAttribute("errorMessage", "Username already exists");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/register.jsp");
                dispatcher.forward(request, response);
                return;
            }

            if (userDAO.isEmailExists(email)) {
                request.setAttribute("errorMessage", "Email already exists");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/register.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Create new user
            User newUser = new User(username, password, email);
            userDAO.saveUser(newUser);

            // Redirect to login page after successful registration
            response.sendRedirect(request.getContextPath() + "/user/login?success=registered");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Registration failed: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void showLoginForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	logger.info("Showing login form");
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/login.jsp");
        dispatcher.forward(request, response);
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        	logger.info("Logging in user");
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // Validate user
            User user = userDAO.validateUser(username, password);

            if (user != null) {
                // Create session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // Redirect to book list
                response.sendRedirect(request.getContextPath() + "/books/list");
            } else {
                request.setAttribute("errorMessage", "Invalid username or password");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user/login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Login failed: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void logoutUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	logger.info("Logging out user");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/user/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
