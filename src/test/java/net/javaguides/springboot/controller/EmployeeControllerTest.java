package net.javaguides.springboot.controller;

import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllEmployees() {

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "John", "Manager", "john@example.com", "Address 1", 50000, 30));
        employees.add(new Employee(2L, "Jane", "Developer", "jane@example.com", "Address 2", 60000, 25));

        when(employeeService.getAllEmployees()).thenReturn(employees);

        ResponseEntity<List<Employee>> responseEntity = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(employees, responseEntity.getBody());
    }

    @Test
    void testGetEmployeeById() {
        Long employeeId = 1L;
        Employee employee = new Employee(employeeId, "John", "Manager", "john@example.com", "Address 1", 50000, 30);

        when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

        ResponseEntity<Employee> responseEntity = employeeController.getEmployeeById(employeeId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(employee, responseEntity.getBody());
    }

    @Test
    void testCreateEmployee() {
        Employee employee = new Employee(1L, "John", "Manager", "john@example.com", "Address 1", 50000, 30);

        when(employeeService.saveEmployee(employee)).thenReturn(employee);

        ResponseEntity<Employee> responseEntity = employeeController.createEmployee(employee);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(employee, responseEntity.getBody());
    }

    @Test
    void testUpdateEmployee() {
        Long employeeId = 1L;
        Employee existingEmployee = new Employee(employeeId, "John", "Manager", "john@example.com", "Address 1", 50000, 30);
        Employee updatedEmployee = new Employee(employeeId, "John Doe", "Manager", "john@example.com", "Address 1", 60000, 32);

        when(employeeService.updateEmployee(employeeId, updatedEmployee)).thenReturn(updatedEmployee);

        ResponseEntity<Employee> responseEntity = employeeController.updateEmployee(employeeId, updatedEmployee);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedEmployee, responseEntity.getBody());
    }

    @Test
    void testDeleteEmployee() {
        Long employeeId = 1L;

        ResponseEntity<Void> responseEntity = employeeController.deleteEmployee(employeeId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }
}
