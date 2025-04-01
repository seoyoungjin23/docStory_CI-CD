package com.ky.docstory.controller.user;

import com.ky.docstory.auth.CurrentUser;
import com.ky.docstory.common.dto.DocStoryResponseBody;
import com.ky.docstory.dto.user.UpdateUserRequest;
import com.ky.docstory.dto.user.UserInvitationResponse;
import com.ky.docstory.dto.user.UserResponse;
import com.ky.docstory.entity.User;
import com.ky.docstory.swagger.BadRequestErrorResponseWrapper;
import com.ky.docstory.swagger.InternalServerErrorResponseWrapper;
import com.ky.docstory.swagger.UnauthorizedErrorResponseWrapper;
import com.ky.docstory.swagger.UserInvitationResponseWrapper;
import com.ky.docstory.swagger.UserLogoutResponseWrapper;
import com.ky.docstory.swagger.UserResponseWrapper;
import com.ky.docstory.swagger.UserSignoutResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User", description = "User 관련 API")
@RequestMapping("/api/users")
public interface UserApi {

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "토큰을 포함한 요청을 받아 서버는 응답만 처리하고 프론트에서 토큰을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 로그아웃되었습니다.",
                    content = @Content(schema = @Schema(implementation = UserLogoutResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(schema = @Schema(implementation = BadRequestErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = UnauthorizedErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponseWrapper.class))),
    })
    ResponseEntity<DocStoryResponseBody<Void>> logout(
            @Parameter(hidden = true)
            @CurrentUser User currentUser,
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String header
    );

    @PostMapping("/signout")
    @Operation(summary = "회원 탈퇴", description = "회원의 정보를 삭제 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 회원 탈퇴되었습니다.",
                    content = @Content(schema = @Schema(implementation = UserSignoutResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(schema = @Schema(implementation = BadRequestErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = UnauthorizedErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponseWrapper.class))),
    })
    ResponseEntity<DocStoryResponseBody<Void>> signout(
            @Parameter(hidden = true)
            @CurrentUser User currentUser,
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String header
    );

    @GetMapping("/me")
    @Operation(summary = "유저 정보 조회", description = "현재 유저의 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 정보를 성공적으로 조회했습니다.",
                    content = @Content(schema = @Schema(implementation = UserResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(schema = @Schema(implementation = BadRequestErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = UnauthorizedErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponseWrapper.class))),
    })
    ResponseEntity<DocStoryResponseBody<UserResponse>> getUserInfo(
            @Parameter(hidden = true)
            @CurrentUser User currentUser
    );

    @PutMapping(
            value = "/me",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "유저 정보 수정", description = "현재 유저의 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 정보를 성공적으로 수정했습니다.",
                    content = @Content(schema = @Schema(implementation = UserResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(schema = @Schema(implementation = BadRequestErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = UnauthorizedErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponseWrapper.class))),
    })
    ResponseEntity<DocStoryResponseBody<UserResponse>> updateUserInfo(
            @Parameter(hidden = true)
            @CurrentUser User currentUser,
            @RequestPart @Valid UpdateUserRequest updateUserRequest,
            @RequestPart(required = false) MultipartFile profileImage
            );

    @GetMapping("/me/invitations")
    @Operation(summary = "받은 팀 초대 목록 조회", description = "현재 로그인한 사용자가 받은 팀 초대 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "초대 목록을 성공적으로 조회했습니다.",
                    content = @Content(schema = @Schema(implementation = UserInvitationResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(schema = @Schema(implementation = BadRequestErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = UnauthorizedErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponseWrapper.class)))
    })
    ResponseEntity<DocStoryResponseBody<List<UserInvitationResponse>>> getMyInvitations(
            @Parameter(hidden = true)
            @CurrentUser User currentUser
    );
}
