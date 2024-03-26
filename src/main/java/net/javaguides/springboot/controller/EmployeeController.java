package net.javaguides.springboot.controller;

import net.javaguides.springboot.entity.EmployeeDTO;
import net.javaguides.springboot.request.EmployeeRequestModel;
import net.javaguides.springboot.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {



    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeRequestModel employeeRequest) {
        EmployeeDTO employeeDTO = convertToEmployeeDTO(employeeRequest);
        EmployeeDTO savedEmployee = employeeService.saveEmployee(employeeDTO);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequestModel employeeRequest) {
        EmployeeDTO employeeDTO = convertToEmployeeDTO(employeeRequest);
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    private EmployeeDTO convertToEmployeeDTO(EmployeeRequestModel employeeRequest) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmpName(employeeRequest.getEmpName());
        employeeDTO.setEmpRole(employeeRequest.getEmpRole());
        employeeDTO.setEmpEmail(employeeRequest.getEmpEmail());
        employeeDTO.setEmpAddress(employeeRequest.getEmpAddress());
        employeeDTO.setEmpSalary(employeeRequest.getEmpSalary());
        employeeDTO.setEmpAge(employeeRequest.getEmpAge());
        return employeeDTO;
    }
}
