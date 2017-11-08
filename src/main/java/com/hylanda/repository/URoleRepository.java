package com.hylanda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hylanda.entity.URole;

@Repository
public interface URoleRepository extends JpaRepository<URole, Long> {
	List<URole> findById(Long rid);
}
