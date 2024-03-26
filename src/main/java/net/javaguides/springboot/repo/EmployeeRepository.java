package net.javaguides.springboot.repo;

import net.javaguides.springboot.entity.EmployeeDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeDTO, Long> {
}