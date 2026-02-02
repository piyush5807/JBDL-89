package com.example.digital_library.dtos;

import com.example.digital_library.models.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitiateTransactionRequest {


    @NotNull
    private Integer bookId;
    @NotNull
    private Integer studentId;
    @NotNull
    private TransactionType transactionType;

    public Transaction to(){
        return Transaction.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .book(
                        Book.builder().id(this.bookId).build()
                ).student(
                        Student.builder().id(this.studentId).build()
                ).transactionType(this.transactionType)
                .transactionStatus(TransactionStatus.PENDING)
                .build();
    }
}
