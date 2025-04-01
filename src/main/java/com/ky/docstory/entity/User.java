package com.ky.docstory.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String providerId;

    private String nickname;

    private String profilePath;

    private String profileFileName;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfile(String filePath, String fileName) {
        this.profilePath = filePath;
        this.profileFileName = fileName;
    }

}
