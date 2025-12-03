package com.example.listacompras.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.listacompras.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
