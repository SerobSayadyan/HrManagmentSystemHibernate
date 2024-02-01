package org.example.dataAccessObject;

import org.example.entities.Department;
import org.example.entities.Manager;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DepartmentDAO {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


    public Department getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Department.class, id);
        }
    }

    public List<Department> getAll() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Department";
            Query<Department> query = session.createQuery(hql, Department.class);
            return query.list();
        }
    }

    public void create(Department department) {
        createUpdateRemove(department, CreateUpdateRemove.CREATE);
    }

    public void update(Department department) {
        createUpdateRemove(department, CreateUpdateRemove.UPDATE);
    }

    public void remove(Department department) {
        createUpdateRemove(department, CreateUpdateRemove.REMOVE);
    }


    private void createUpdateRemove(Department department, CreateUpdateRemove cur) {
        Transaction transaction = null;
        checkDepartment(department);
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            switch (cur) {
                case CREATE -> session.persist(department);
                case UPDATE -> session.merge(department);
                case REMOVE -> session.remove(department);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private void checkDepartment(Department department) {
        Manager manager = department.getDepartmentHead();
        ManagerDAO managerDAO = new ManagerDAO();
        if (manager != null) {
            managerDAO.update(manager);
        } else {
            throw new RuntimeException("Didn't found manager");
        }
    }

}
