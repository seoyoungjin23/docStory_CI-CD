package com.ky.docstory.dto.user;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "유저 정보 수정 요청 DTO")
public record UpdateUserRequest(

        @NotBlank(message = "유저 닉네임은 필수입니다.")
        @Size(min = 2, max = 20, message = "유저 닉네임은 2자 이상 20자 이하여야 합니다.")
        @Schema(description = "유저 닉네임", example = "비버", required = true)
        String nickname
) {}
