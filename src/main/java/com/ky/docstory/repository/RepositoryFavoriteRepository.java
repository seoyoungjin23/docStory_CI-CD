package com.ky.docstory.repository;

import com.ky.docstory.entity.Repository;
import com.ky.docstory.entity.RepositoryFavorite;
import com.ky.docstory.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryFavoriteRepository extends JpaRepository<RepositoryFavorite, UUID> {
    Optional<RepositoryFavorite> findByUserAndRepository(User user, Repository repository);

    boolean existsByUserAndRepository(User user, Repository repository);

    List<RepositoryFavorite> findAllByUser(User user);
}
