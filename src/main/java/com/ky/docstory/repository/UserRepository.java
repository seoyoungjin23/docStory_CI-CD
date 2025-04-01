package com.ky.docstory.repository;

import com.ky.docstory.entity.User;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByProviderId(String providerId);
}
