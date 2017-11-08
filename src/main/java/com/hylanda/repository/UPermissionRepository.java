package com.hylanda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hylanda.entity.UPermission;

@Repository
public interface UPermissionRepository extends JpaRepository<UPermission, Long> {
}
