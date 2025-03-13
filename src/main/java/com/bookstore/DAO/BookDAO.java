package com.bookstore.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.bookstore.model.Book;
import com.bookstore.util.HibernateUtil;

public class BookDAO {
	
    // Save or update a book
    public void saveBook(Book book) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    // Get a book by ID
    public Book getBookById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Book.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Get a book by ID with reviews (eager loading)
    public Book getBookWithReviews(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Book> query = session.createQuery(
                "SELECT b FROM Book b LEFT JOIN FETCH b.reviews WHERE b.bookId = :id", 
                Book.class
            );
            query.setParameter("id", id);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Get all books
    public List<Book> getAllBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Book WHERE status = 1", Book.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Get books with pagination
    public List<Book> getBooks(int page, int booksPerPage) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Book> query = session.createQuery("FROM Book WHERE status = 1", Book.class);
            query.setFirstResult((page - 1) * booksPerPage);
            query.setMaxResults(booksPerPage);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Get total number of books
    public long getTotalBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(b) FROM Book b WHERE b.status = 1", Long.class);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    // Delete a book (soft delete by setting status to 0)
    public void deleteBook(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Book book = session.get(Book.class, id);
            if (book != null) {
                book.setStatus(0); // Soft delete
                session.update(book);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    // Search books by title with pagination
    public List<Book> searchBooksByTitle(String title, int page, int booksPerPage) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Book> query = session.createQuery(
                "FROM Book WHERE status = 1 AND LOWER(title) LIKE LOWER(:title)", 
                Book.class
            );
            query.setParameter("title", "%" + title + "%");
            query.setFirstResult((page - 1) * booksPerPage);
            query.setMaxResults(booksPerPage);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Get total number of books matching title search
    public long getTotalBooksByTitle(String title) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(b) FROM Book b WHERE b.status = 1 AND LOWER(b.title) LIKE LOWER(:title)", 
                Long.class
            );
            query.setParameter("title", "%" + title + "%");
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    // Search books by author with pagination
    public List<Book> searchBooksByAuthor(String author, int page, int booksPerPage) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Book> query = session.createQuery(
                "FROM Book WHERE status = 1 AND LOWER(author) LIKE LOWER(:author)", 
                Book.class
            );
            query.setParameter("author", "%" + author + "%");
            query.setFirstResult((page - 1) * booksPerPage);
            query.setMaxResults(booksPerPage);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Get total number of books matching author search
    public long getTotalBooksByAuthor(String author) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(b) FROM Book b WHERE b.status = 1 AND LOWER(b.author) LIKE LOWER(:author)", 
                Long.class
            );
            query.setParameter("author", "%" + author + "%");
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    // Search books by genre with pagination
    public List<Book> searchBooksByGenre(String genre, int page, int booksPerPage) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Book> query = session.createQuery(
                "FROM Book WHERE status = 1 AND LOWER(genre) LIKE LOWER(:genre)", 
                Book.class
            );
            query.setParameter("genre", "%" + genre + "%");
            query.setFirstResult((page - 1) * booksPerPage);
            query.setMaxResults(booksPerPage);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Get total number of books matching genre search
    public long getTotalBooksByGenre(String genre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(b) FROM Book b WHERE b.status = 1 AND LOWER(b.genre) LIKE LOWER(:genre)", 
                Long.class
            );
            query.setParameter("genre", "%" + genre + "%");
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Book> searchBooks(String searchTerm, int page, int booksPerPage) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Book> query = session.createQuery(
                "FROM Book WHERE status = 1 AND " +
                "(LOWER(title) LIKE LOWER(:searchTerm) OR " +
                "LOWER(author) LIKE LOWER(:searchTerm) OR " +
                "LOWER(genre) LIKE LOWER(:searchTerm))", 
                Book.class
            );
            query.setParameter("searchTerm", "%" + searchTerm + "%");
            query.setFirstResult((page - 1) * booksPerPage);
            query.setMaxResults(booksPerPage);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Get total count for search results across multiple fields
    public long getTotalSearchResults(String searchTerm) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(b) FROM Book b WHERE b.status = 1 AND " +
                "(LOWER(b.title) LIKE LOWER(:searchTerm) OR " +
                "LOWER(b.author) LIKE LOWER(:searchTerm) OR " +
                "LOWER(b.genre) LIKE LOWER(:searchTerm))", 
                Long.class
            );
            query.setParameter("searchTerm", "%" + searchTerm + "%");
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}