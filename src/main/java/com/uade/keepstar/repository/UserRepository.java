package com.uade.keepstar.repository;

import com.uade.keepstar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByActiveTrue();
    Optional<User> findByIdAndActiveTrue(Long id);
    Optional<User> findByUsernameAndActiveTrue(String username);
}
