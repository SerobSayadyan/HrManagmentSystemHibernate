package org.example.cli;

import org.example.dataAccessObject.*;
import org.example.entities.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjectCLI {

    private static final DepartmentDAO departmentDao = new DepartmentDAO();
    private static final EmployeeDAO employeeDao = new EmployeeDAO();
    private static final ProjectDAO projectDao = new ProjectDAO();


    public static void addProjectCLI() {
        Scanner scanner = new Scanner(System.in);

        Project project = new Project();

        System.out.println("Enter project details:");

        System.out.print("Project Name: ");
        String projectName = scanner.nextLine();
        project.setProjectName(projectName);

        System.out.print("Start Date (YYYY-MM-DD): ");
        String startDateStr = scanner.nextLine();
        Date startDate = Date.valueOf(startDateStr);
        project.setStartDate(startDate);

        System.out.print("End Date (YYYY-MM-DD): ");
        String endDateStr = scanner.nextLine();
        Date endDate = Date.valueOf(endDateStr);
        project.setEndDate(endDate);

        System.out.print("Budget: ");
        double budget = scanner.nextDouble();
        project.setBudget(budget);

        List<Department> departments = departmentDao.getAll();
        if (departments.isEmpty()) {
            System.out.println("No departments found. Please add departments before creating a project.");
            return;
        }

        System.out.println("Available Departments:");
        for (Department department : departments) {
            System.out.println("Department ID: " + department.getDepartmentId() + ", Department Name: " + department.getDepartmentName());
        }

        System.out.print("Enter Department ID for the project: ");
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
        project.setDepartment(department);

        List<Employee> employees = employeeDao.getAll();
        if (employees.isEmpty()) {
            System.out.println("No employees found. Please add employees before creating a project.");
            return;
        }

        System.out.println("Available Employees:");
        for (Employee employee : employees) {
            System.out.println("Employee ID: " + employee.getEmployeeId() + ", Employee Name: " + employee.getName());
        }

        System.out.print("Enter Employee IDs to assign to the project (comma-separated): ");
        String employeeIdsStr = scanner.nextLine();
        String[] employeeIdsArray = employeeIdsStr.split(",");
        List<Employee> assignedEmployees = new ArrayList<>();

        for (String employeeIdStr : employeeIdsArray) {
            try {
                Long employeeId = Long.parseLong(employeeIdStr.trim());
                Employee assignedEmployee = employeeDao.getById(employeeId);
                if (assignedEmployee != null) {
                    assignedEmployees.add(assignedEmployee);
                } else {
                    System.out.println("Employee with ID " + employeeId + " not found. Skipping...");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for Employee ID: " + employeeIdStr + ". Skipping...");
            }
        }

        project.setEmployees(assignedEmployees);

        projectDao.create(project);

        System.out.println("Project added successfully.");

        scanner.close();
    }

    public static void updateProjectCLI() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Project ID to update: ");
        long projectId;
        do {
            try {
                projectId = scanner.nextLong();
                scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid numeric ID.");
                scanner.nextLine();
            }
        } while (true);

        Project project = projectDao.getById(projectId);
        if (project == null) {
            System.out.println("Project not found. Please make sure the ID is correct.");
            return;
        }

        System.out.println("Update project details:");

        System.out.print("Change Project Name? (YES/NO): ");
        String operation = scanner.nextLine().toLowerCase();
        switch (operation) {
            case "yes" -> {
                System.out.print("Enter new Project Name: ");
                String newProjectName = scanner.nextLine();
                project.setProjectName(newProjectName);
                System.out.println("Project Name updated.");
            }
            case "no" -> System.out.println("Project Name not changed.");
            default -> System.out.println("Invalid input. Project Name remains unchanged.");
        }

        System.out.print("Change Start Date? (YES/NO): ");
        operation = scanner.nextLine().toLowerCase();
        switch (operation) {
            case "yes" -> {
                System.out.print("Enter new Start Date (YYYY-MM-DD): ");
                String newStartDateStr = scanner.nextLine();
                Date newStartDate = Date.valueOf(newStartDateStr);
                project.setStartDate(newStartDate);
                System.out.println("Start Date updated.");
            }
            case "no" -> System.out.println("Start Date not changed.");
            default -> System.out.println("Invalid input. Start Date remains unchanged.");
        }

        System.out.print("Change End Date? (YES/NO): ");
        operation = scanner.nextLine().toLowerCase();
        switch (operation) {
            case "yes" -> {
                System.out.print("Enter new End Date (YYYY-MM-DD): ");
                String newEndDateStr = scanner.nextLine();
                Date newEndDate = Date.valueOf(newEndDateStr);
                project.setEndDate(newEndDate);
                System.out.println("End Date updated.");
            }
            case "no" -> System.out.println("End Date not changed.");
            default -> System.out.println("Invalid input. End Date remains unchanged.");
        }

        System.out.print("Change Budget? (YES/NO): ");
        operation = scanner.nextLine().toLowerCase();
        switch (operation) {
            case "yes" -> {
                System.out.print("Enter new Budget: ");
                double newBudget = scanner.nextDouble();
                project.setBudget(newBudget);
                System.out.println("Budget updated.");
            }
            case "no" -> System.out.println("Budget not changed.");
            default -> System.out.println("Invalid input. Budget remains unchanged.");
        }

        System.out.print("Change Department? (YES/NO): ");
        operation = scanner.nextLine().toLowerCase();
        switch (operation) {
            case "yes" -> {
                System.out.print("Enter new Department ID: ");
                long newDepartmentId;
                do {
                    try {
                        newDepartmentId = scanner.nextLong();
                        scanner.nextLine();
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter a valid numeric ID.");
                        scanner.nextLine();
                    }
                } while (true);

                Department newDepartment = departmentDao.getById(newDepartmentId);
                if (newDepartment == null) {
                    System.out.println("Invalid Department ID. Department remains unchanged.");
                } else {
                    project.setDepartment(newDepartment);
                    System.out.println("Department updated.");
                }
            }
            case "no" -> System.out.println("Department not changed.");
            default -> System.out.println("Invalid input. Department remains unchanged.");
        }

        List<Employee> employees = employeeDao.getAll();
        if (employees.isEmpty()) {
            System.out.println("No employees found. Please add employees before updating a project.");
            return;
        }

        System.out.println("Available Employees:");
        for (Employee employee : employees) {
            System.out.println("Employee ID: " + employee.getEmployeeId() + ", Employee Name: " + employee.getName());
        }

        System.out.print("Enter Employee IDs to assign to the project (comma-separated): ");
        String employeeIdsStr = scanner.nextLine();
        String[] employeeIdsArray = employeeIdsStr.split(",");
        List<Employee> assignedEmployees = new ArrayList<>();

        for (String employeeIdStr : employeeIdsArray) {
            try {
                Long employeeId = Long.parseLong(employeeIdStr.trim());
                Employee assignedEmployee = employeeDao.getById(employeeId);
                if (assignedEmployee != null) {
                    assignedEmployees.add(assignedEmployee);
                } else {
                    System.out.println("Employee with ID " + employeeId + " not found. Skipping...");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for Employee ID: " + employeeIdStr + ". Skipping...");
            }
        }

        project.setEmployees(assignedEmployees);

        projectDao.update(project);

        System.out.println("Project updated successfully.");

        scanner.close();
    }

    public static void listProjectCLI() {
        List<Project> projects = projectDao.getAll();

        if (projects.isEmpty()) {
            System.out.println("No projects found.");
        } else {
            System.out.println("List of Projects:");

            for (Project project : projects) {
                System.out.println("Project ID: " + project.getProjectId());
                System.out.println("Name: " + project.getProjectName());
                System.out.println("Start Date: " + project.getStartDate());
                System.out.println("End Date: " + project.getEndDate());
                System.out.println("Budget: " + project.getBudget());

                Department department = project.getDepartment();
                if (department != null) {
                    System.out.println("Department: " + department.getDepartmentName());
                } else {
                    System.out.println("Department: Not Assigned");
                }

                List<Employee> employees = project.getEmployees();
                if (employees != null && !employees.isEmpty()) {
                    System.out.println("Assigned Employees:");
                    for (Employee employee : employees) {
                        System.out.println("Employee ID: " + employee.getEmployeeId() + ", Employee Name: " + employee.getName());
                    }
                } else {
                    System.out.println("Assigned Employees: None");
                }
                System.out.println();
            }
        }

    }


    public static void deleteProjectCLI() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Project ID to delete: ");
        long projectId;
        do {
            try {
                projectId = scanner.nextLong();
                scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid numeric ID.");
                scanner.nextLine();
            }
        } while (true);

        Project project = projectDao.getById(projectId);
        if (project == null) {
            System.out.println("Project not found. Please make sure the ID is correct.");
        } else {
            System.out.println("Are you sure you want to delete this project? (YES/NO): ");
            String confirmDelete = scanner.nextLine().toLowerCase();

            switch (confirmDelete) {
                case "yes" -> {
                    projectDao.remove(project);
                    System.out.println("Project deleted successfully.");
                }
                case "no" -> System.out.println("Deletion canceled.");
                default -> System.out.println("Invalid input. Deletion canceled.");
            }
        }

        scanner.close();
    }

    public static void assignEmployeeToProjectCLI() {
        Scanner scanner = new Scanner(System.in);

        List<Employee> employees = employeeDao.getAll();
        if (employees.isEmpty()) {
            System.out.println("No employees found. Please add employees before assigning to a project.");
            scanner.close();
            return;
        }

        System.out.println("Available Employees:");
        for (Employee employee : employees) {
            System.out.println("Employee ID: " + employee.getEmployeeId() + ", Employee Name: " + employee.getName());
        }

        System.out.print("Enter Employee ID to assign to a project: ");
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

        Employee employee = employeeDao.getById(employeeId);
        if (employee == null) {
            System.out.println("Employee not found. Please make sure the ID is correct.");
            scanner.close();
            return;
        }

        List<Project> projects = projectDao.getAll();
        if (projects.isEmpty()) {
            System.out.println("No projects found. Please add projects before assigning an employee.");
            scanner.close();
            return;
        }

        System.out.println("Available Projects:");
        for (Project project : projects) {
            System.out.println("Project ID: " + project.getProjectId() + ", Project Name: " + project.getProjectName());
        }

        System.out.print("Enter Project ID to assign the employee to: ");
        Long projectId;
        do {
            try {
                projectId = scanner.nextLong();
                scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid numeric ID.");
                scanner.nextLine();
            }
        } while (true);

        Project project = projectDao.getById(projectId);
        if (project == null) {
            System.out.println("Project not found. Please make sure the ID is correct.");
            scanner.close();
            return;
        }

        project.getEmployees().add(employee);
        projectDao.update(project);

        System.out.println("Employee assigned to the project successfully.");

        scanner.close();
    }

}
