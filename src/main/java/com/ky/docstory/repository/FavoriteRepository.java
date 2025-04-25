package com.ky.docstory.repository;

import com.ky.docstory.entity.Repository;
import com.ky.docstory.entity.Favorite;
import com.ky.docstory.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    Optional<Favorite> findByUserAndRepository(User user, Repository repository);

    boolean existsByUserAndRepository(User user, Repository repository);

    List<Favorite> findAllByUser(User user);
}
