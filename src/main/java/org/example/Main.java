package org.example;

import org.example.cli.MainCommandLineInterface;
import org.example.dataAccessObject.DepartmentDAO;
import org.example.dataAccessObject.ManagerDAO;
import org.example.entities.Department;
import org.example.entities.Employee;
import org.example.entities.ManagementLevel;
import org.example.entities.Manager;
import org.example.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        MainCommandLineInterface.start();
        HibernateUtil.shutDown();
    }
}