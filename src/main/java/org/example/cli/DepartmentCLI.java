package org.example.cli;

import org.example.dataAccessObject.DepartmentDAO;
import org.example.dataAccessObject.EmployeeDAO;
import org.example.dataAccessObject.ManagerDAO;
import org.example.entities.Department;
import org.example.entities.Employee;
import org.example.entities.Manager;

import java.util.List;
import java.util.Scanner;

public class DepartmentCLI {

    private static final DepartmentDAO departmentDao = new DepartmentDAO();
    private static final ManagerDAO managerDao = new ManagerDAO();
    private static final EmployeeDAO employeeDao = new EmployeeDAO();


    static void addDepartmentCLI() {
        Scanner scanner = new Scanner(System.in);
        Department department = new Department();

        System.out.println("Enter department details:");


        System.out.print("Department Name: ");
        String departmentName = scanner.nextLine();
        department.setDepartmentName(departmentName);

        System.out.print("Location: ");
        String location = scanner.nextLine();
        department.setLocation(location);

        Long departmentHeadId = null;
        do {
            System.out.print("Department Head ID: ");
            try {
                departmentHeadId = scanner.nextLong();
                scanner.nextLine();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid numeric ID.");
                scanner.nextLine();
            }
        } while (true);

        Manager departmentHead = managerDao.getById(departmentHeadId);
        if (departmentHead == null) {
            System.out.println("Invalid Department Head ID. Please make sure the ID is correct.");
            return;
        }
        department.setDepartmentHead(departmentHead);

        departmentDao.create(department);

        System.out.println("Department added successfully!");

        scanner.close();
    }

    static void updateDepartmentCLI() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Department ID to update: ");
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

        // Assuming you have a service method to get the department by ID
        Department department = departmentDao.getById(departmentId);
        if (department == null) {
            System.out.println("Department not found. Please make sure the ID is correct.");
            return; // Exit the method if the department ID is not found
        }

        System.out.println("Update department details:");

        System.out.print("Change Department Name? (YES/NO): ");
        String operation = scanner.nextLine().toLowerCase();
        switch (operation) {
            case "yes" -> {
                System.out.print("Enter new Department Name: ");
                String newDepartmentName = scanner.nextLine();
                department.setDepartmentName(newDepartmentName);
                System.out.println("Department Name updated.");
            }
            case "no" -> System.out.println("Department Name not changed.");
            default -> System.out.println("Invalid input. Department Name remains unchanged.");
        }

        System.out.print("Change Location? (YES/NO): ");
        operation = scanner.nextLine().toLowerCase();
        switch (operation) {
            case "yes" -> {
                System.out.print("Enter new Location: ");
                String newLocation = scanner.nextLine();
                department.setLocation(newLocation);
                System.out.println("Location updated.");
            }
            case "no" -> System.out.println("Location not changed.");
            default -> System.out.println("Invalid input. Location remains unchanged.");
        }

        System.out.print("Change Department Head? (YES/NO): ");
        operation = scanner.nextLine().toLowerCase();
        switch (operation) {
            case "yes" -> {
                System.out.print("Enter new Department Head ID: ");
                long newDepartmentHeadId;
                do {
                    try {
                        newDepartmentHeadId = scanner.nextLong();
                        scanner.nextLine();
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter a valid numeric ID.");
                        scanner.nextLine();
                    }
                } while (true);

                Manager newDepartmentHead = managerDao.getById(newDepartmentHeadId);
                if (newDepartmentHead == null) {
                    System.out.println("Invalid Department Head ID. Department Head remains unchanged.");
                } else {
                    department.setDepartmentHead(newDepartmentHead);
                    System.out.println("Department Head updated.");
                }
            }
            case "no" -> System.out.println("Department Head not changed.");
            default -> System.out.println("Invalid input. Department Head remains unchanged.");
        }

        departmentDao.update(department);

        System.out.println("Department updated successfully.");

        scanner.close();
    }

    static void listDepartmentsCLI() {
        List<Department> departments = departmentDao.getAll();

        if (departments.isEmpty()) {
            System.out.println("No departments found.");
        } else {
            System.out.println("List of Departments:");

            for (Department department : departments) {
                System.out.println("Department ID: " + department.getDepartmentId());
                System.out.println("Name: " + department.getDepartmentName());
                System.out.println("Location: " + department.getLocation());

                Manager departmentHead = department.getDepartmentHead();
                if (departmentHead != null) {
                    System.out.println("Department Head: " + departmentHead.getName());
                } else {
                    System.out.println("Department Head: Not Assigned");
                }
                System.out.println();
            }
        }
    }

    static void deleteDepartmentCLI() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Department ID to delete: ");
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
        } else {
            System.out.println("Are you sure you want to delete this department? (YES/NO): ");
            String confirmDelete = scanner.nextLine().toLowerCase();

            switch (confirmDelete) {
                case "yes" -> {
                    departmentDao.remove(department);
                    System.out.println("Department deleted successfully.");
                }
                case "no" -> System.out.println("Deletion canceled.");
                default -> System.out.println("Invalid input. Deletion canceled.");
            }
        }

        scanner.close();
    }

    public static void assignEmployeeToDepartmentCLI() {
        Scanner scanner = new Scanner(System.in);

        List<Employee> employees = employeeDao.getAll();
        if (employees.isEmpty()) {
            System.out.println("No employees found. Please add employees before assigning to a department.");
            return;
        }

        long employeeId;
        System.out.print("Enter Employee ID to assign: ");
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

        Employee employee = employeeDao.getById(employeeId);
        if (employee == null) {
            System.out.println("Employee not found. Please make sure the ID is correct.");
            return;
        }

        System.out.print("Enter Department ID to assign the employee to: ");
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
            return;
        }

        employee.setDepartment(department);
        employeeDao.update(employee);

        System.out.println("Employee assigned to the department successfully.");

        scanner.close();
    }

    public static void assignManagerToDepartmentCLI() {
        Scanner scanner = new Scanner(System.in);

        // Assuming you have a service method to get a list of all managers
        List<Manager> managers = managerDao.getAll();
        if (managers.isEmpty()) {
            System.out.println("No managers found. Please add managers before assigning to a department.");
            scanner.close();
            return;
        }

        System.out.println("Available Managers:");
        for (Manager manager : managers) {
            System.out.println("Manager ID: " + manager.getEmployeeId() + ", Manager Name: " + manager.getName());
        }

        System.out.print("Enter Manager ID to assign to a department: ");
        Long managerId;
        do {
            try {
                managerId = scanner.nextLong();
                scanner.nextLine(); // Consume the newline character
                break; // Break the loop if a valid ID is entered
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid numeric ID.");
                scanner.nextLine(); // Consume the invalid input
            }
        } while (true);

        // Assuming you have a service method to get the manager by ID
        Manager manager = managerDao.getById(managerId);
        if (manager == null) {
            System.out.println("Manager not found. Please make sure the ID is correct.");
            scanner.close();
            return;
        }

        // Assuming you have a service method to get a list of all departments
        List<Department> departments = departmentDao.getAll();
        if (departments.isEmpty()) {
            System.out.println("No departments found. Please add departments before assigning a manager.");
            scanner.close();
            return;
        }

        System.out.println("Available Departments:");
        for (Department department : departments) {
            System.out.println("Department ID: " + department.getDepartmentId() + ", Department Name: " + department.getDepartmentName());
        }

        System.out.print("Enter Department ID to assign the manager to: ");
        Long departmentId;
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

        department.setDepartmentHead(manager);
        manager.setDepartment(department);

        departmentDao.update(department);
        managerDao.update(manager);

        System.out.println("Manager assigned to the department successfully.");
        scanner.close();
    }
}
