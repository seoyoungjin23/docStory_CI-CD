package com.ky.docstory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "review")
public class Review extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pp_id", nullable = false)
    private Proposal proposal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @Column(name = "parent_id")
    private UUID parentId;

    public void updateComment(String comment) {
        this.comment = comment;
    }

    public boolean isReply() {
        return this.parentId != null;
    }
}
