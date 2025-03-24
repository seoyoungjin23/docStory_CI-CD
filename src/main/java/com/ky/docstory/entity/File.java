package com.ky.docstory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "file")
public class File extends BaseEntity {

    @Column(nullable = false)
    private String originFilename;

    @Column(nullable = false)
    private String saveFilename;

    @Column(nullable = false, unique = true)
    private String filepath;

    @Column(nullable = false)
    private String fileType;
}
