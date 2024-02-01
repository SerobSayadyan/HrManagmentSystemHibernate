package org.example.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("manager")
public class Manager extends Employee {


    @Column(name = "managed_department")
    private String managedDepartment;

    @Enumerated(EnumType.STRING)
    @Column(name = "management_level")
    private ManagementLevel managementLevel;

    public Manager(Employee employee, String managedDepartment, ManagementLevel managementLevel) {
        super(employee.getEmployeeId(), employee.getName(), employee.getEmail(),
                employee.getPhoneNumber(), employee.getHireDate(), employee.getJobTitle(), employee.getDepartment());
        this.managedDepartment = managedDepartment;
        this.managementLevel = managementLevel;
    }

    @Override
    public String toString() {
        return "Employee Manager { " +
                "employeeId = " + getEmployeeId() +
                ", name = '" + getName() + '\'' +
                ", email = '" + getEmail() + '\'' +
                ", phoneNumber = '" + getPhoneNumber() + '\'' +
                ", hireDate = " + getHireDate() +
                ", jobTitle = '" + getJobTitle() + '\'' +
                ", departmentID = " + (getDepartment() == null ? null : getDepartment().getDepartmentId()) +
                " managedDepartment = '" + managedDepartment + '\'' +
                ", managementLevel = " + managementLevel +
                " }";
    }
}
