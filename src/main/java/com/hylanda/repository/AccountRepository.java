package com.hylanda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hylanda.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
