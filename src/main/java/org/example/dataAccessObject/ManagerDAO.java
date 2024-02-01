package org.example.dataAccessObject;

import org.example.entities.Manager;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ManagerDAO {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


    public Manager getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Manager.class, id);
        }
    }

    public List<Manager> getAll() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Manager";
            Query<Manager> query = session.createQuery(hql, Manager.class);
            return query.list();
        }
    }

    public void create(Manager manager) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(manager);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(Manager manager) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(manager);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void remove(Manager manager) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(manager);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
