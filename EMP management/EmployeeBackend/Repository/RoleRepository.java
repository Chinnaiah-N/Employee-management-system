package com.EmployeeManagement.EmployeeBackend.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.EmployeeManagement.EmployeeBackend.Model.ERole;
import com.EmployeeManagement.EmployeeBackend.Model.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
	  Optional<Role> findByName(ERole name);
	}
