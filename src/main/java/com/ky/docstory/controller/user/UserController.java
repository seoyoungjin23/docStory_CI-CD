package com.ky.docstory.controller.user;

import com.ky.docstory.auth.CurrentUser;
import com.ky.docstory.common.dto.DocStoryResponseBody;
import com.ky.docstory.dto.user.UpdateUserRequest;
import com.ky.docstory.dto.user.UserInvitationResponse;
import com.ky.docstory.dto.user.UserResponse;
import com.ky.docstory.entity.User;
import com.ky.docstory.service.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi{

    private final UserService userService;

    @Override
    public ResponseEntity<DocStoryResponseBody<Void>> logout(
            @CurrentUser User currentUser,
            @RequestHeader("Authorization") String header) {
        return ResponseEntity.status(HttpStatus.OK).body(DocStoryResponseBody.success(null));
    }

    @Override
    public ResponseEntity<DocStoryResponseBody<Void>> signout(User currentUser, String header) {
        userService.deleteUserInfo(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(DocStoryResponseBody.success(null));
    }

    @Override
    public ResponseEntity<DocStoryResponseBody<UserResponse>> getUserInfo(
            @CurrentUser User currentUser) {
        UserResponse userResponse = userService.getUserInfo(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(DocStoryResponseBody.success(userResponse));
    }

    @Override
    public ResponseEntity<DocStoryResponseBody<UserResponse>> updateUserInfo(
            @CurrentUser User currentUser,
            @RequestPart UpdateUserRequest updateUserRequest,
            @RequestPart(required = false) MultipartFile profileImage) {
        UserResponse userResponse = userService.updateUserInfo(currentUser, updateUserRequest, profileImage);
        return ResponseEntity.status(HttpStatus.OK).body(DocStoryResponseBody.success(userResponse));
    }

    @Override
    public ResponseEntity<DocStoryResponseBody<List<UserInvitationResponse>>> getMyInvitations(User currentUser) {
        List<UserInvitationResponse> userInvitationResponses = userService.getInvitations(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(DocStoryResponseBody.success(userInvitationResponses));
    }
}
