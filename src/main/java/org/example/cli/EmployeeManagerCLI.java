package org.example.cli;

import org.example.dataAccessObject.DepartmentDAO;
import org.example.dataAccessObject.EmployeeDAO;
import org.example.dataAccessObject.ManagerDAO;
import org.example.entities.Department;
import org.example.entities.Employee;
import org.example.entities.ManagementLevel;
import org.example.entities.Manager;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagerCLI {

    private static final DepartmentDAO departmentDao = new DepartmentDAO();
    private static final ManagerDAO managerDao = new ManagerDAO();
    private static final EmployeeDAO employeeDao = new EmployeeDAO();

    public static void addEmployeeOrManagerCLI() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose employee type (EMPLOYEE/MANAGER): ");
        String employeeType = scanner.nextLine().toUpperCase();

        Employee employee;
        if (employeeType.equals("EMPLOYEE")) {
            employee = new Employee();
        } else if (employeeType.equals("MANAGER")) {
            employee = new Manager();
        } else {
            System.out.println("Invalid employee type. Please enter either EMPLOYEE or MANAGER.");
            scanner.close();
            return;
        }

        System.out.println("Enter employee details:");

        System.out.print("Name: ");
        String name = scanner.nextLine();
        employee.setName(name);

        System.out.print("Email: ");
        String email = scanner.nextLine();
        employee.setEmail(email);

        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();
        employee.setPhoneNumber(phoneNumber);

        System.out.print("Hire Date (YYYY-MM-DD): ");
        String hireDateStr = scanner.nextLine();
        Date hireDate = Date.valueOf(hireDateStr);
        employee.setHireDate(hireDate);

        System.out.print("Job Title: ");
        String jobTitle = scanner.nextLine();
        employee.setJobTitle(jobTitle);

        List<Department> departments = departmentDao.getAll();
        if (departments.isEmpty()) {
            System.out.println("No departments found. Please add departments before adding an employee.");
            scanner.close();
            return;
        }

        System.out.println("Available Departments:");
        for (Department department : departments) {
            System.out.println("Department ID: " + department.getDepartmentId() + ", Department Name: " + department.getDepartmentName());
        }

        System.out.print("Enter Department ID for the employee: ");
        long departmentId;
        do {
            try {
                departmentId = scanner.nextLong();
                scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid numeric ID.");
                scanner.nextLine();
            }
        } while (true);

        Department department = departmentDao.getById(departmentId);
        if (department == null) {
            System.out.println("Department not found. Please make sure the ID is correct.");
            scanner.close();
            return;
        }
        employee.setDepartment(department);

        if (employee instanceof Manager manager) {

            System.out.print("Managed Department: ");
            String managedDepartment = scanner.nextLine();
            manager.setManagedDepartment(managedDepartment);

            System.out.print("Management Level (TOP_LEVEL/MID_LEVEL/FIRST_LEVEL): ");
            String managementLevelStr = scanner.nextLine().toUpperCase();
            try {
                ManagementLevel managementLevel = ManagementLevel.valueOf(managementLevelStr);
                manager.setManagementLevel(managementLevel);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Management Level. Please enter either TOP_LEVEL, MID_LEVEL, or FIRST_LEVEL.");
                scanner.close();
                return;
            }

            managerDao.create(manager);
        } else {
            employeeDao.create(employee);
        }

        System.out.println("Employee/Manager added successfully.");

        scanner.close();

    }

    public static void updateEmployeeOrManagerCLI() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose employee type (EMPLOYEE/MANAGER): ");
        String employeeType = scanner.nextLine().toUpperCase();

        System.out.print("Enter Employee ID to update: ");
        long employeeId;
        do {
            try {
                employeeId = scanner.nextLong();
                scanner.nextLine(); // Consume the newline character
                break; // Break the loop if a valid ID is entered
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid numeric ID.");
                scanner.nextLine(); // Consume the invalid input
            }
        } while (true);

        Employee employee;
        if (employeeType.equals("EMPLOYEE")) {
            employee = employeeDao.getById(employeeId);
        } else if (employeeType.equals("MANAGER")) {
            employee = managerDao.getById(employeeId);
        } else {
            System.out.println("Invalid employee type. Please enter either EMPLOYEE or MANAGER.");
            scanner.close();
            return;
        }

        if (employee == null) {
            System.out.println("Employee not found. Please make sure the ID is correct.");
            scanner.close();
            return;
        }

        System.out.println("Update employee details:");

        System.out.print("Change Name? (YES/NO): ");
        String operation = scanner.nextLine().toLowerCase();
        switch (operation) {
            case "yes" -> {
                System.out.print("Enter new Name: ");
                String newName = scanner.nextLine();
                employee.setName(newName);
                System.out.println("Name updated.");
            }
            case "no" -> System.out.println("Name not changed.");
            default -> System.out.println("Invalid input. Name remains unchanged.");
        }

        System.out.print("Change Email? (YES/NO): ");
        operation = scanner.nextLine().toLowerCase();
        switch (operation) {
            case "yes" -> {
                System.out.print("Enter new Email: ");
                String newEmail = scanner.nextLine();
                employee.setEmail(newEmail);
                System.out.println("Email updated.");
            }
            case "no" -> System.out.println("Email not changed.");
            default -> System.out.println("Invalid input. Email remains unchanged.");
        }

        System.out.print("Change Phone Number? (YES/NO): ");
        operation = scanner.nextLine().toLowerCase();
        switch (operation) {
            case "yes" -> {
                System.out.print("Enter new Phone Number: ");
                String newPhoneNumber = scanner.nextLine();
                employee.setPhoneNumber(newPhoneNumber);
                System.out.println("Phone Number updated.");
            }
            case "no" -> System.out.println("Phone Number not changed.");
            default -> System.out.println("Invalid input. Phone Number remains unchanged.");
        }

        System.out.print("Change Hire Date? (YES/NO): ");
        operation = scanner.nextLine().toLowerCase();
        switch (operation) {
            case "yes" -> {
                System.out.print("Enter new Hire Date (YYYY-MM-DD): ");
                String newHireDateStr = scanner.nextLine();
                Date newHireDate = Date.valueOf(newHireDateStr);
                employee.setHireDate(newHireDate);
                System.out.println("Hire Date updated.");
            }
            case "no" -> System.out.println("Hire Date not changed.");
            default -> System.out.println("Invalid input. Hire Date remains unchanged.");
        }

        System.out.print("Change Job Title? (YES/NO): ");
        operation = scanner.nextLine().toLowerCase();
        switch (operation) {
            case "yes" -> {
                System.out.print("Enter new Job Title: ");
                String newJobTitle = scanner.nextLine();
                employee.setJobTitle(newJobTitle);
                System.out.println("Job Title updated.");
            }
            case "no" -> System.out.println("Job Title not changed.");
            default -> System.out.println("Invalid input. Job Title remains unchanged.");
        }

        if (employee instanceof Manager manager) {

            System.out.print("Change Managed Department? (YES/NO): ");
            operation = scanner.nextLine().toLowerCase();
            switch (operation) {
                case "yes" -> {
                    System.out.print("Enter new Managed Department: ");
                    String newManagedDepartment = scanner.nextLine();
                    manager.setManagedDepartment(newManagedDepartment);
                    System.out.println("Managed Department updated.");
                }
                case "no" -> System.out.println("Managed Department not changed.");
                default -> System.out.println("Invalid input. Managed Department remains unchanged.");
            }

            System.out.print("Change Management Level? (YES/NO): ");
            operation = scanner.nextLine().toLowerCase();
            switch (operation) {
                case "yes" -> {
                    System.out.print("Enter new Management Level (TOP_LEVEL/MID_LEVEL/FIRST_LEVEL): ");
                    String newManagementLevelStr = scanner.nextLine().toUpperCase();
                    try {
                        ManagementLevel newManagementLevel = ManagementLevel.valueOf(newManagementLevelStr);
                        manager.setManagementLevel(newManagementLevel);
                        System.out.println("Management Level updated.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid Management Level. Please enter either TOP_LEVEL, MID_LEVEL, or FIRST_LEVEL.");
                    }
                }
                case "no" -> System.out.println("Management Level not changed.");
                default -> System.out.println("Invalid input. Management Level remains unchanged.");
            }

            // Assuming you have a service method to update the manager
            managerDao.update(manager);
        } else {
            // Assuming you have a service method to update the employee
            employeeDao.update(employee);
        }

        System.out.println("Employee/Manager updated successfully.");

        scanner.close();
    }

    public static void listEmployeeOrManagerCLI() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose employee type (EMPLOYEE/MANAGER): ");
        String employeeType = scanner.nextLine().toUpperCase();

        if (employeeType.equals("EMPLOYEE")) {
            List<Employee> employees = employeeDao.getAll();

            if (employees.isEmpty()) {
                System.out.println("No employees found.");
            } else {
                System.out.println("List of Employees:");

                for (Employee employee : employees) {
                    System.out.println("Employee ID: " + employee.getEmployeeId());
                    System.out.println("Name: " + employee.getName());
                    System.out.println("Email: " + employee.getEmail());
                    System.out.println("Phone Number: " + employee.getPhoneNumber());
                    System.out.println("Hire Date: " + employee.getHireDate());
                    System.out.println("Job Title: " + employee.getJobTitle());

                    Department department = employee.getDepartment();
                    if (department != null) {
                        System.out.println("Department: " + department.getDepartmentName());
                    } else {
                        System.out.println("Department: Not Assigned");
                    }

                    System.out.println();
                }
            }
        } else if (employeeType.equals("MANAGER")) {
            List<Manager> managers = managerDao.getAll();

            if (managers.isEmpty()) {
                System.out.println("No managers found.");
            } else {
                System.out.println("List of Managers:");

                for (Manager manager : managers) {
                    System.out.println("Employee ID: " + manager.getEmployeeId());
                    System.out.println("Name: " + manager.getName());
                    System.out.println("Email: " + manager.getEmail());
                    System.out.println("Phone Number: " + manager.getPhoneNumber());
                    System.out.println("Hire Date: " + manager.getHireDate());
                    System.out.println("Job Title: " + manager.getJobTitle());
                    System.out.println("Managed Department: " + manager.getManagedDepartment());
                    System.out.println("Management Level: " + manager.getManagementLevel());

                    Department department = manager.getDepartment();
                    if (department != null) {
                        System.out.println("Department: " + department.getDepartmentName());
                    } else {
                        System.out.println("Department: Not Assigned");
                    }

                    System.out.println();
                }
            }
        } else {
            System.out.println("Invalid employee type. Please enter either EMPLOYEE or MANAGER.");
        }

        scanner.close();
    }

    public static void deleteEmployeeOrManagerCLI() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose employee type (EMPLOYEE/MANAGER): ");
        String employeeType = scanner.nextLine().toUpperCase();

        System.out.print("Enter Employee ID to delete: ");
        Long employeeId;
        do {
            try {
                employeeId = scanner.nextLong();
                scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid numeric ID.");
                scanner.nextLine();
            }
        } while (true);

        if (employeeType.equals("EMPLOYEE")) {
            Employee employee = employeeDao.getById(employeeId);

            if (employee == null) {
                System.out.println("Employee not found. Please make sure the ID is correct.");
            } else {
                System.out.println("Are you sure you want to delete this employee? (YES/NO): ");
                String confirmDelete = scanner.nextLine().toLowerCase();

                switch (confirmDelete) {
                    case "yes" -> {

                        employeeDao.remove(employee);
                        System.out.println("Employee deleted successfully.");
                    }
                    case "no" -> System.out.println("Deletion canceled.");
                    default -> System.out.println("Invalid input. Deletion canceled.");
                }
            }
        } else if (employeeType.equals("MANAGER")) {
            Manager manager = managerDao.getById(employeeId);

            if (manager == null) {
                System.out.println("Manager not found. Please make sure the ID is correct.");
            } else {
                System.out.println("Are you sure you want to delete this manager? (YES/NO): ");
                String confirmDelete = scanner.nextLine().toLowerCase();

                switch (confirmDelete) {
                    case "yes" -> {
                        managerDao.remove(manager);
                        System.out.println("Manager deleted successfully.");
                    }
                    case "no" -> System.out.println("Deletion canceled.");
                    default -> System.out.println("Invalid input. Deletion canceled.");
                }
            }
        } else {
            System.out.println("Invalid employee type. Please enter either EMPLOYEE or MANAGER.");
        }

        scanner.close();
    }
}
