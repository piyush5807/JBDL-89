package com.example;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InitiateTxnRequestDTO {

    @NotNull
    private Integer receiverId; // user who will be receiving the money
    // TODO: Remove from here and get it from the token
    private Integer senderId; // user who will be sending the money

    @Min(1)
    @NotNull
    private Long amount;

    private String reason;


    public Txn to() {
        return Txn.builder()
                .amount(this.amount)
                .extTxnId(UUID.randomUUID().toString())
                .receiverId(this.receiverId)
                .senderId(this.senderId)
                .reason(this.reason)
                .txnStatus(TxnStatus.PENDING)
                .build();
    }
}
