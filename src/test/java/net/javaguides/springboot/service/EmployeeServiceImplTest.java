package net.javaguides.springboot.service;

import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.repo.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "John", "Manager", "john@example.com", "123 Main St", 50000, 35));
        employees.add(new Employee(2L, "hari", "Engineer", "hari@example.com", "456 north St", 60000, 28));

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getEmpName());
        assertEquals("Manager", result.get(0).getEmpRole());
        assertEquals("john@example.com", result.get(0).getEmpEmail());
        assertEquals("123 Main St", result.get(0).getEmpAddress());
        assertEquals(50000, result.get(0).getEmpSalary());
        assertEquals(35, result.get(0).getEmpAge());
    }

    @Test
    void testGetEmployeeById() {
        Long id = 1L;
        Employee employee = new Employee(id, "John", "Manager", "john@example.com", "123 Main St", 50000, 35);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployeeById(id);

        assertNotNull(result);
        assertEquals("John", result.getEmpName());
        assertEquals("Manager", result.getEmpRole());
        assertEquals("john@example.com", result.getEmpEmail());
        assertEquals("123 Main St", result.getEmpAddress());
        assertEquals(50000, result.getEmpSalary());
        assertEquals(35, result.getEmpAge());
    }

    @Test
    void testSaveEmployee() {
        Employee employee = new Employee(null, "John", "Manager", "john@example.com", "123 Main St", 50000, 35);
        Employee savedEmployee = new Employee(1L, "John", "Manager", "john@example.com", "123 Main St", 50000, 35);

        when(employeeRepository.save(employee)).thenReturn(savedEmployee);

        Employee result = employeeService.saveEmployee(employee);

        assertNotNull(result);
        assertEquals(1L, result.getEmpId());
    }

    @Test
    void testUpdateEmployee() {
        Long id = 1L;
        Employee existingEmployee = new Employee(id, "John", "Manager", "john@example.com", "123 Main St", 50000, 35);
        Employee updatedEmployee = new Employee(id, "Alice", "Engineer", "alice@example.com", "456 north St", 60000, 28);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(existingEmployee)).thenReturn(updatedEmployee);

        Employee result = employeeService.updateEmployee(id, updatedEmployee);

        assertNotNull(result);
        assertEquals("Alice", result.getEmpName());
        assertEquals("Engineer", result.getEmpRole());
        assertEquals("alice@example.com", result.getEmpEmail());
        assertEquals("456 north St", result.getEmpAddress());
        assertEquals(60000, result.getEmpSalary());
        assertEquals(28, result.getEmpAge());
    }

    @Test
    void testDeleteEmployee() {
        Long id = 1L;

        employeeService.deleteEmployee(id);

        verify(employeeRepository, times(1)).deleteById(id);
    }
}
