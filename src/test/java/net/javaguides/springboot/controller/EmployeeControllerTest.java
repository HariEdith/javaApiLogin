package net.javaguides.springboot.controller;

import net.javaguides.springboot.entity.EmployeeDTO;
import net.javaguides.springboot.request.EmployeeRequestModel;
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
        // Prepare
        List<EmployeeDTO> employees = new ArrayList<>();
        employees.add(new EmployeeDTO(1L, "John", "Manager", "john@example.com", "Address 1", 50000, 30));
        employees.add(new EmployeeDTO(2L, "Jane", "Developer", "jane@example.com", "Address 2", 60000, 25));

        when(employeeService.getAllEmployees()).thenReturn(employees);

        // Execute
        ResponseEntity<List<EmployeeDTO>> responseEntity = employeeController.getAllEmployees();

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(employees, responseEntity.getBody());
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void testGetEmployeeById() {

        Long employeeId = 1L;
        EmployeeDTO employee = new EmployeeDTO(employeeId, "John", "Manager", "john@example.com", "Address 1", 50000, 30);

        when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

        ResponseEntity<EmployeeDTO> responseEntity = employeeController.getEmployeeById(employeeId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(employee, responseEntity.getBody());
        verify(employeeService, times(1)).getEmployeeById(employeeId);
    }

    @Test
    void testCreateEmployee() {

        EmployeeRequestModel employeeRequest = new EmployeeRequestModel();
        employeeRequest.setEmpName("John");
        employeeRequest.setEmpRole("Manager");
        employeeRequest.setEmpEmail("john@example.com");
        employeeRequest.setEmpAddress("Address 1");
        employeeRequest.setEmpSalary(50000);
        employeeRequest.setEmpAge(30);

        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "John", "Manager", "john@example.com", "Address 1", 50000, 30);

        when(employeeService.saveEmployee(any())).thenReturn(employeeDTO);

        ResponseEntity<EmployeeDTO> responseEntity = employeeController.createEmployee(employeeRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(employeeDTO, responseEntity.getBody());
        verify(employeeService, times(1)).saveEmployee(any());
    }

    @Test
    void testUpdateEmployee() {

        Long employeeId = 1L;
        EmployeeRequestModel employeeRequest = new EmployeeRequestModel();
        employeeRequest.setEmpName("John");
        employeeRequest.setEmpRole("Manager");
        employeeRequest.setEmpEmail("john@example.com");
        employeeRequest.setEmpAddress("Address 1");
        employeeRequest.setEmpSalary(60000);
        employeeRequest.setEmpAge(32);

        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO(employeeId, "John", "Manager", "john@example.com", "Address 1", 60000, 32);
        when(employeeService.updateEmployee(eq(employeeId), any())).thenReturn(updatedEmployeeDTO);

        ResponseEntity<EmployeeDTO> responseEntity = employeeController.updateEmployee(employeeId, employeeRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedEmployeeDTO, responseEntity.getBody());
        verify(employeeService, times(1)).updateEmployee(eq(employeeId), any());
    }

    @Test
    void testDeleteEmployee() {

        Long employeeId = 1L;

        ResponseEntity<Void> responseEntity = employeeController.deleteEmployee(employeeId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }
}
