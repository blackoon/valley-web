package com.hylanda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hylanda.entity.Department;

@Repository("departmentRepository")
public interface DepartmentRepository  extends JpaRepository<Department,Long> {
}
