package com.payupi.user.entity;


import com.payupi.user.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "UserAccounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(name = "bank_account_id", nullable = false)
    private String bankAccountId;

    @Column(name = "bank_id", nullable = false)
    private Long bankId;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @Column(name = "linked_at", nullable = false, updatable = false)
    private LocalDateTime linkedAt;

    @PrePersist
    public void onCreate() {
        linkedAt = LocalDateTime.now();

        if (status == null) {
            status = AccountStatus.ACTIVE;
        }

        if (isPrimary == null) {
            isPrimary = false;
        }
    }
}
