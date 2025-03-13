package com.bookstore.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.bookstore.model.Review;
import com.bookstore.util.HibernateUtil;

public class ReviewDAO {
    
    // Save or update a review
    public void saveReview(Review review) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(review);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    // Get a review by ID
    public Review getReviewById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Review.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Get all reviews for a book
    public List<Review> getReviewsByBookId(int bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Review> query = session.createQuery(
                "FROM Review r WHERE r.book.bookId = :bookId", 
                Review.class
            );
            query.setParameter("bookId", bookId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Get all reviews by a user
    public List<Review> getReviewsByUserId(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Review> query = session.createQuery(
                "FROM Review r WHERE r.user.userId = :userId", 
                Review.class
            );
            query.setParameter("userId", userId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Delete a review
    public void deleteReview(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Review review = session.get(Review.class, id);
            if (review != null) {
                session.delete(review);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    // Get average rating for a book
    public Double getAverageRatingForBook(int bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Double> query = session.createQuery(
                "SELECT AVG(r.rating) FROM Review r WHERE r.book.bookId = :bookId", 
                Double.class
            );
            query.setParameter("bookId", bookId);
            Double result = query.uniqueResult();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}