package net.javaguides.springboot.service;

import net.javaguides.springboot.entity.EmployeeDTO;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO saveEmployee(EmployeeDTO employee);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employee);
    void deleteEmployee(Long id);
}