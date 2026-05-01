package com.educandoweb.coursespring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educandoweb.coursespring.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
