package com.ky.docstory.service.repository;

import com.ky.docstory.dto.repository.MyRepositoryResponse;
import com.ky.docstory.dto.repository.RepositoryCreateRequest;
import com.ky.docstory.dto.repository.RepositoryDetailResponse;
import com.ky.docstory.dto.repository.RepositoryResponse;
import com.ky.docstory.dto.repository.RepositoryUpdateRequest;
import com.ky.docstory.entity.User;
import java.util.List;
import java.util.UUID;

public interface RepositoryService {
    RepositoryResponse createRepository(RepositoryCreateRequest request, User owner);

    List<MyRepositoryResponse> getMyRepositories(User currentUser);

    RepositoryDetailResponse getRepositoryDetail(UUID repositoryId, User currentUser);

    RepositoryResponse updateRepository(UUID repositoryId, RepositoryUpdateRequest request, User currentUser);

    void deleteRepository(UUID repositoryId, User currentUser);
}