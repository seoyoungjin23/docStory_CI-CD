package com.ky.docstory.controller.repository;

import com.ky.docstory.auth.CurrentUser;
import com.ky.docstory.common.dto.DocStoryResponseBody;
import com.ky.docstory.dto.repository.RepositoryCreateRequest;
import com.ky.docstory.dto.repository.RepositoryResponse;
import com.ky.docstory.entity.User;
import com.ky.docstory.service.repository.RepositoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RepositoryController implements RepositoryApi {

    private final RepositoryService repositoryService;

    @Override
    public ResponseEntity<DocStoryResponseBody<RepositoryResponse>> createRepository(
            @CurrentUser User currentUser,
            @RequestBody @Valid RepositoryCreateRequest request) {
        RepositoryResponse response = repositoryService.createRepository(request, currentUser);
        return ResponseEntity.ok(DocStoryResponseBody.success(response));
    }
}
