package net.javaguides.springboot.service;

import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        return employeeOptional.orElse(null);
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = getEmployeeById(id);
        if (existingEmployee != null) {
            existingEmployee.setEmpName(employee.getEmpName());
            existingEmployee.setEmpRole(employee.getEmpRole());
            existingEmployee.setEmpEmail(employee.getEmpEmail());
            existingEmployee.setEmpAddress(employee.getEmpAddress());
            existingEmployee.setEmpSalary(employee.getEmpSalary());
            existingEmployee.setEmpAge(employee.getEmpAge());
            return employeeRepository.save(existingEmployee);
        }
        return null;
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}