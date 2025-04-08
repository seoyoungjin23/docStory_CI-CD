package com.ky.docstory.service.repository;

import com.ky.docstory.dto.repository.MyRepositoryResponse;
import com.ky.docstory.dto.repository.RepositoryCreateRequest;
import com.ky.docstory.dto.repository.RepositoryResponse;
import com.ky.docstory.entity.User;
import java.util.List;
import java.util.UUID;

public interface RepositoryService {
    RepositoryResponse createRepository(RepositoryCreateRequest request, User owner);

    List<MyRepositoryResponse> getMyRepositories(User currentUser);

    List<MyRepositoryResponse> getMyFavoriteRepositories(User currentUser);

    MyRepositoryResponse addFavorite(UUID repositoryId, User currentUser);

    void removeFavorite(UUID repositoryId, User currentUser);

}