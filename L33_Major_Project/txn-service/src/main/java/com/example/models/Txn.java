package com.example.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Txn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String extTxnId;

    @Enumerated(EnumType.STRING)
    private TxnStatus txnStatus;

    private String reason;

    @CreationTimestamp
    private Date timestamp;

    @UpdateTimestamp
    private Date updatedTimestamp;

    private Integer senderId;

    private Integer receiverId;

    private Long amount;
}
