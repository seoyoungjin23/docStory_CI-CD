package com.ky.docstory.service.repository;

import com.ky.docstory.dto.repository.RepositoryCreateRequest;
import com.ky.docstory.dto.repository.RepositoryResponse;
import com.ky.docstory.entity.User;

public interface RepositoryService {
    RepositoryResponse createRepository(RepositoryCreateRequest request, User owner);
}