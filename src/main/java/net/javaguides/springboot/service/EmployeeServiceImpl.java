package net.javaguides.springboot.service;

import net.javaguides.springboot.entity.EmployeeDTO;
import net.javaguides.springboot.repo.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    private  final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Optional<EmployeeDTO> employeeOptional = employeeRepository.findById(id);
        return employeeOptional.orElse(null);
    }

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employee) {
        EmployeeDTO existingEmployee = getEmployeeById(id);
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