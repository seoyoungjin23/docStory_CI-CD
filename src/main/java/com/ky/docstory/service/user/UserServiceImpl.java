package com.ky.docstory.service.user;

import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import com.ky.docstory.dto.user.ProfileImage;
import com.ky.docstory.dto.user.UpdateUserRequest;
import com.ky.docstory.dto.user.UserInvitationResponse;
import com.ky.docstory.dto.user.UserResponse;
import com.ky.docstory.entity.TeamInvite;
import com.ky.docstory.entity.User;
import com.ky.docstory.repository.TeamInviteRepository;
import com.ky.docstory.repository.UserRepository;
import io.awspring.cloud.s3.S3Template;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final TeamInviteRepository teamInviteRepository;
    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public UserResponse getUserInfo(User currentUser) {
        User user = findUserByProviderId(currentUser.getProviderId());
        String profileImage = encodeImageFromS3(user.getProfilePath());
        return UserResponse.from(user, profileImage);
    }

    @Override
    @Transactional
    public UserResponse updateUserInfo(User currentUser, UpdateUserRequest updateUserRequest,
                                       MultipartFile profileImage) {

        currentUser.updateNickname(updateUserRequest.nickname());

        if (profileImage != null && !profileImage.isEmpty()) {
            String prevProfilePath = currentUser.getProfilePath();

            ProfileImage savedProfileImage = uploadProfileImage(profileImage);
            currentUser.updateProfile(savedProfileImage.getFilePath(), savedProfileImage.getSaveFilename());

            deleteFileFromS3(prevProfilePath);
        }

        User savedUser = userRepository.save(currentUser);
        String profileImageBase64 = encodeImageFromS3(savedUser.getProfilePath());

        return UserResponse.from(savedUser, profileImageBase64);
    }

    @Override
    @Transactional
    public void deleteUserInfo(User currentUser) {
        User user = findUserByProviderId(currentUser.getProviderId());
        userRepository.delete(user);
    }

    private String encodeImageFromS3(String filePath) {
        try {
            Resource resource = s3Template.download(bucketName, filePath);
            try (InputStream inputStream = resource.getInputStream()) {
                byte[] imageBytes = inputStream.readAllBytes();
                return Base64.getEncoder().encodeToString(imageBytes);
            }
        } catch (IOException e) {
            throw new BusinessException(DocStoryResponseCode.FILE_DOWNLOAD_FAILED);
        }
    }

    @Override
    public List<UserInvitationResponse> getInvitations(User currentUser) {
        return teamInviteRepository.findAllByInviteeAndStatus(currentUser, TeamInvite.Status.PENDING)
                .stream()
                .map(UserInvitationResponse::from)
                .toList();
    }

    private User findUserByProviderId(String providerId) {
        return userRepository.findByProviderId(providerId)
                .orElseThrow(() -> new BusinessException(DocStoryResponseCode.NOT_FOUND));
    }

    private ProfileImage uploadProfileImage(MultipartFile profileImage) {
        if (profileImage == null || profileImage.isEmpty()) return null;

        String originalFilename = profileImage.getOriginalFilename();
        if (!originalFilename.contains(".")) {
            throw new BusinessException(DocStoryResponseCode.PARAMETER_ERROR);
        }

        int idx = originalFilename.lastIndexOf(".");
        String extension = originalFilename.substring(idx + 1).toLowerCase();

        String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yy-MM-dd"));
        String saveFilename = UUID.randomUUID() + "." + extension;
        String filePath = String.format("profileImage/%s/%s", dateDir, saveFilename);

        try (InputStream inputStream = profileImage.getInputStream()) {
            s3Template.upload(bucketName, filePath, inputStream);
        } catch (IOException e) {
            throw new BusinessException(DocStoryResponseCode.FILE_UPLOAD_FAILED);
        }

        return new ProfileImage(filePath, saveFilename);
    }

    private void deleteFileFromS3(String filePath) {
        try {
            s3Template.deleteObject(bucketName, filePath);
        } catch (Exception e) {
            throw new BusinessException(DocStoryResponseCode.FILE_DELETE_FAILED);
        }
    }
}
