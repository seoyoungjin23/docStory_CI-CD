package com.ky.docstory.service.repository;

import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import com.ky.docstory.dto.repository.MyRepositoryResponse;
import com.ky.docstory.dto.repository.RepositoryCreateRequest;
import com.ky.docstory.dto.repository.RepositoryResponse;
import com.ky.docstory.entity.Repository;
import com.ky.docstory.entity.RepositoryFavorite;
import com.ky.docstory.entity.Team;
import com.ky.docstory.entity.User;
import com.ky.docstory.repository.RepositoryFavoriteRepository;
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
    private final RepositoryFavoriteRepository repositoryFavoriteRepository;

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
    @Transactional
    public List<MyRepositoryResponse> getMyRepositories(User currentUser) {
        List<Team> myTeams = teamRepository.findAllByUserWithRepository(currentUser);

        Set<UUID> favoriteIds = repositoryFavoriteRepository.findAllByUser(currentUser).stream()
                .map(fav -> fav.getRepository().getId())
                .collect(Collectors.toSet());

        return myTeams.stream()
                .map(team -> {
                    boolean isFavorite = favoriteIds.contains(team.getRepository().getId());
                    return MyRepositoryResponse.from(team, isFavorite);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyRepositoryResponse> getMyFavoriteRepositories(User currentUser) {
        List<RepositoryFavorite> favorites = repositoryFavoriteRepository.findAllByUser(currentUser);

        return favorites.stream()
                .map(fav -> {
                    Repository repo = fav.getRepository();
                    Team team = teamRepository.findByRepositoryAndUser(repo, currentUser)
                            .orElseThrow(() -> new BusinessException(DocStoryResponseCode.FORBIDDEN));

                    return MyRepositoryResponse.from(team, true);
                })
                .toList();
    }

    @Override
    @Transactional
    public MyRepositoryResponse addFavorite(UUID repositoryId, User currentUser) {
        Repository repository = validateRepositoryAccess(repositoryId, currentUser.getId());

        boolean exists = repositoryFavoriteRepository.existsByUserAndRepository(currentUser, repository);
        if (exists) {
            throw new BusinessException(DocStoryResponseCode.BAD_REQUEST);
        }

        RepositoryFavorite favorite = RepositoryFavorite.builder()
                .user(currentUser)
                .repository(repository)
                .build();

        repositoryFavoriteRepository.save(favorite);

        Team team = teamRepository.findByRepositoryAndUser(repository, currentUser)
                .orElseThrow(() -> new BusinessException(DocStoryResponseCode.FORBIDDEN));

        return MyRepositoryResponse.from(team, true);
    }

    @Override
    @Transactional
    public void removeFavorite(UUID repositoryId, User currentUser) {
        Repository repository = validateRepositoryAccess(repositoryId, currentUser.getId());

        RepositoryFavorite favorite = repositoryFavoriteRepository.findByUserAndRepository(currentUser, repository)
                .orElseThrow(() -> new BusinessException(DocStoryResponseCode.NOT_FOUND));

        repositoryFavoriteRepository.delete(favorite);
    }

    private Repository validateRepositoryAccess(UUID repositoryId, UUID userId) {
        Repository repository = repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new BusinessException(DocStoryResponseCode.NOT_FOUND));

        teamRepository.findByRepositoryAndUserId(repository, userId)
                .orElseThrow(() -> new BusinessException(DocStoryResponseCode.FORBIDDEN));

        return repository;
    }
}