package org.example.cli;

import java.util.Scanner;

public class MainCommandLineInterface {
    public static void start() {
        Scanner scanner = new Scanner(System.in);
        String mainMessage = """
                    Main Menu:
                    1. Add Department
                    2. Update Department
                    3. List Departments
                    4. Delete Department
                    5. Assign Employee to Department
                    6. Add Project
                    7. Update Project
                    8. List Projects
                    9. Delete Project
                    10. Add Employee/Manager
                    11. Update Employee/Manager
                    12. List Employees/Managers
                    13. Delete Employee/Manager
                    14. Assign Employee to Project
                    15. Assign Manager to Department
                    0. Exit
                    """;
        while (true) {
            System.out.println(mainMessage);
            System.out.print("Enter your choice: ");
            String operation = scanner.nextLine().trim();
            System.out.println();
            switch (operation) {
                case "1" -> DepartmentCLI.addDepartmentCLI();
                case "2" -> DepartmentCLI.updateDepartmentCLI();
                case "3" -> DepartmentCLI.listDepartmentsCLI();
                case "4" -> DepartmentCLI.deleteDepartmentCLI();
                case "5" -> DepartmentCLI.assignEmployeeToDepartmentCLI();
                case "6" -> ProjectCLI.addProjectCLI();
                case "7" -> ProjectCLI.updateProjectCLI();
                case "8" -> ProjectCLI.listProjectCLI();
                case "9" -> ProjectCLI.deleteProjectCLI();
                case "10" -> EmployeeManagerCLI.addEmployeeOrManagerCLI();
                case "11" -> EmployeeManagerCLI.updateEmployeeOrManagerCLI();
                case "12" -> EmployeeManagerCLI.listEmployeeOrManagerCLI();
                case "13" -> EmployeeManagerCLI.deleteEmployeeOrManagerCLI();
                case "14" -> ProjectCLI.assignEmployeeToProjectCLI();
                case "15" -> DepartmentCLI.assignManagerToDepartmentCLI();
                case "0" -> {
                    System.out.println("Bye!!!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Wrong operation - the '" + operation + "' is not correct operator");
            }
        }
    }
}
