package com.ky.docstory.entity;

import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "team_invite", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"repository_id", "invitee_id"})
})
public class TeamInvite extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repository_id", nullable = false)
    private Repository repository;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inviter_id", nullable = false)
    private User inviter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitee_id", nullable = false)
    private User invitee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    public void accept() {
        if (this.status != Status.PENDING) {
            throw new BusinessException(DocStoryResponseCode.ALREADY_HANDLED);
        }
        this.status = Status.ACCEPTED;
    }

    public void reject() {
        if (this.status != Status.PENDING) {
            throw new BusinessException(DocStoryResponseCode.ALREADY_HANDLED);
        }
        this.status = Status.REJECTED;
    }
}
