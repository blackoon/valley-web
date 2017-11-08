package com.hylanda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hylanda.entity.UUser;

@Repository
public interface UUserRepository extends JpaRepository<UUser, Long> {
	UUser findByNickname(String username);
}
