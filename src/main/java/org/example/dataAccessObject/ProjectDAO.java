package org.example.dataAccessObject;

import org.example.entities.Department;
import org.example.entities.Manager;
import org.example.entities.Project;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProjectDAO {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


    public Project getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Project.class, id);
        }
    }

    public List<Project> getAll() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Project ";
            Query<Project> query = session.createQuery(hql, Project.class);
            return query.list();
        }
    }

    private void createUpdateRemove(Project project, CreateUpdateRemove cur) {
        Transaction transaction = null;
        checkDepartment(project.getDepartment());
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            switch (cur) {
                case CREATE -> session.persist(project);
                case UPDATE -> session.merge(project);
                case REMOVE -> session.remove(project);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void create(Project project) {
        createUpdateRemove(project, CreateUpdateRemove.CREATE);
    }

    public void update(Project project) {
        createUpdateRemove(project, CreateUpdateRemove.UPDATE);
    }

    public void remove(Project project) {
        createUpdateRemove(project, CreateUpdateRemove.REMOVE);
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
