package com.example.digital_library.services;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TxnServiceHelper {

    @Value("${library.book-return.fine-per-day}")
    private Integer finePerDay;

    @Value("${library.book-issuance.max-threshold}")
    private Integer maxThreshold;

    public Long calculateFine(Date dueDate){

        Date currentDate = new Date();
        long timeDifference = currentDate.getTime() - dueDate.getTime(); // epoch time diff

        if(timeDifference < 0){
            return 0L;
        }

        long numberOfDaysPassed = TimeUnit.MILLISECONDS.toDays(timeDifference);
        return (numberOfDaysPassed -  maxThreshold) * finePerDay * 100; // in paise // lowest denomination
    }
}
