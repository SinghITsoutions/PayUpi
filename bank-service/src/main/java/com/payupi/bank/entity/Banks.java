package com.payupi.bank.entity;


import jakarta.persistence.*;
import lombok.*;






@Entity
@Table(name = "banks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Banks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String bankName;

    @Column(nullable = false, unique = true, length = 20)
    private String bankCode;

    @Column(nullable = false, unique = true, length = 20)
    private String ifscPrefix;






}
