package com.ky.docstory.service.repository;

import com.ky.docstory.dto.repository.RepositoryCreateRequest;
import com.ky.docstory.dto.repository.RepositoryResponse;
import com.ky.docstory.entity.Repository;
import com.ky.docstory.entity.Team;
import com.ky.docstory.entity.User;
import com.ky.docstory.repository.TeamRepository;
import com.ky.docstory.repository.RepositoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService {

    private final RepositoryRepository repositoryRepository;
    private final TeamRepository teamRepository;

    @Override
    @Transactional
    public RepositoryResponse createRepository(RepositoryCreateRequest request, User owner) {
        Repository repository = Repository.builder()
                .name(request.name())
                .description(request.description())
                .owner(owner)
                .build();

        repositoryRepository.save(repository);

        Team team = Team.builder()
                .repository(repository)
                .user(owner)
                .role(Team.Role.ADMIN)
                .build();

        teamRepository.save(team);

        return RepositoryResponse.from(repository);
    }
}