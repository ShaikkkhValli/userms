package com.shaikkh.userms.repository;

import com.shaikkh.userms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<List<User>> findByNameContainingIgnoreCase(String name);
}
