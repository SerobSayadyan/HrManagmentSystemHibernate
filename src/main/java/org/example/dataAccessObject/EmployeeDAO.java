package org.example.dataAccessObject;

import org.example.entities.Department;
import org.example.entities.Employee;
import org.example.entities.Manager;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeeDAO {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


    public Employee getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Employee.class, id);
        }
    }

    public List<Employee> getAll() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Employee";
            Query<Employee> query = session.createQuery(hql, Employee.class);
            return query.list();
        }
    }

    private void createUpdateRemove(Employee employee, CreateUpdateRemove cur) {
        Transaction transaction = null;
        checkDepartment(employee.getDepartment());
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            switch (cur) {
                case CREATE -> session.persist(employee);
                case UPDATE -> session.merge(employee);
                case REMOVE -> session.remove(employee);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void create(Employee employee) {
        createUpdateRemove(employee, CreateUpdateRemove.CREATE);
    }

    public void update(Employee employee) {
        createUpdateRemove(employee, CreateUpdateRemove.UPDATE);
    }

    public void remove(Employee employee) {
        createUpdateRemove(employee, CreateUpdateRemove.REMOVE);
    }

    private void checkDepartment(Department department) {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        ManagerDAO managerDAO = new ManagerDAO();
        Department departmentById = departmentDAO.getById(department.getDepartmentId());
        if (departmentById != null) {
            departmentDAO.update(department);
        } else {
            throw new RuntimeException("Didn't found department");
        }

        Manager manager = department.getDepartmentHead();
        if (manager != null) {
            managerDAO.update(manager);
        } else {
            throw new RuntimeException("Didn't found manager");
        }
    }

}
