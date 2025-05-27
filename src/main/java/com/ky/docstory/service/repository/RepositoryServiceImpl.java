package com.ky.docstory.service.repository;

import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import com.ky.docstory.common.validator.RepositoryValidator;
import com.ky.docstory.dto.repository.MyRepositoryResponse;
import com.ky.docstory.dto.repository.RepositoryCreateRequest;
import com.ky.docstory.dto.repository.RepositoryDetailResponse;
import com.ky.docstory.dto.repository.RepositoryResponse;
import com.ky.docstory.dto.repository.RepositoryUpdateRequest;
import com.ky.docstory.entity.File;
import com.ky.docstory.entity.Repository;
import com.ky.docstory.entity.Team;
import com.ky.docstory.entity.User;
import com.ky.docstory.repository.FavoriteRepository;
import com.ky.docstory.repository.FileRepository;
import com.ky.docstory.repository.RepositoryRepository;
import com.ky.docstory.repository.TeamRepository;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService {

    private final RepositoryRepository repositoryRepository;
    private final TeamRepository teamRepository;
    private final FavoriteRepository favoriteRepository;
    private final FileRepository fileRepository;
    private final RepositoryValidator repositoryValidator;

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

    @Override
    @Transactional(readOnly = true)
    public List<MyRepositoryResponse> getMyRepositories(User currentUser) {
        List<Team> myTeams = teamRepository.findAllByUserWithRepository(currentUser);

        Set<UUID> favoriteIds = favoriteRepository.findAllByUser(currentUser).stream()
                .map(fav -> fav.getRepository().getId())
                .collect(Collectors.toSet());

        return myTeams.stream()
                .map(team -> {
                    boolean isFavorite = favoriteIds.contains(team.getRepository().getId());
                    
                    List<File> repositoryFiles = fileRepository.findByRepositoryIdAndParentFileIsNull(team.getRepository().getId());
                    Set<String> fileTypes = repositoryFiles.stream()
                            .map(file -> file.getFileType().name())
                            .collect(Collectors.toSet());
                    
                    return MyRepositoryResponse.from(team, isFavorite, fileTypes);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RepositoryDetailResponse getRepositoryDetail(UUID repositoryId, User currentUser) {
        Repository repository = repositoryValidator.validateRepositoryAccess(repositoryId, currentUser.getId());
        
        List<File> rootFiles = fileRepository.findByRepositoryIdAndParentFileIsNull(repositoryId);
        
        return RepositoryDetailResponse.from(repository, rootFiles);
    }

    @Override
    @Transactional
    public RepositoryResponse updateRepository(UUID repositoryId, RepositoryUpdateRequest request, User currentUser) {
        Repository repository = repositoryValidator.validateRepositoryAccess(repositoryId, currentUser.getId());
        
        if (!repository.getOwner().getId().equals(currentUser.getId())) {
            throw new BusinessException(DocStoryResponseCode.FORBIDDEN);
        }
        
        repository.updateRepository(request.name(), request.description());
        
        return RepositoryResponse.from(repository);
    }

    @Override
    @Transactional
    public void deleteRepository(UUID repositoryId, User currentUser) {
        Repository repository = repositoryValidator.validateRepositoryAccess(repositoryId, currentUser.getId());
        
        if (!repository.getOwner().getId().equals(currentUser.getId())) {
            throw new BusinessException(DocStoryResponseCode.FORBIDDEN);
        }
        
        repository.markAsDeleted();
    }
}