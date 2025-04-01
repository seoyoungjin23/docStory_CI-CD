package com.ky.docstory.service.user;

import com.ky.docstory.dto.user.UpdateUserRequest;
import com.ky.docstory.dto.user.UserInvitationResponse;
import com.ky.docstory.dto.user.UserResponse;
import com.ky.docstory.entity.User;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserResponse getUserInfo(User currentUser);
    UserResponse updateUserInfo(User currentUser, UpdateUserRequest updateUserRequest, MultipartFile profileImage);
    void deleteUserInfo(User currentUser);
    List<UserInvitationResponse> getInvitations(User currentUser);
}
