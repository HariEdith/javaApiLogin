package net.javaguides.springboot.entity;

import javax.persistence.*;

@Entity
@Table(name="employee", uniqueConstraints = @UniqueConstraint(columnNames = "empId"))
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;
    @Column(name = "emp_name")
    private String empName;
    private String empRole;
    private String empEmail;
    private String empAddress;
    private int empSalary;
    private int empAge;


    public Employee(Long empId, String empName, String empRole, String empEmail, String empAddress, int empSalary, int empAge) {
        this.empId = empId;
        this.empName = empName;
        this.empRole = empRole;
        this.empEmail = empEmail;
        this.empAddress = empAddress;
        this.empSalary = empSalary;
        this.empAge = empAge;
    }

    public Employee() {
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpRole() {
        return empRole;
    }

    public void setEmpRole(String empRole) {
        this.empRole = empRole;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public int getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(int empSalary) {
        this.empSalary = empSalary;
    }

    public int getEmpAge() {
        return empAge;
    }

    public void setEmpAge(int empAge) {
        this.empAge = empAge;
    }

}
