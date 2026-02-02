package com.example.digital_library.services;

import jakarta.validation.Valid;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TxnServiceHelper {

    @Value("${library.book-return.fine-per-day}")
    private Integer finePerDay;

    @Value("${library.book-issuance.max-threshold}")
    private Integer maxThreshold;

    @Value("${library.book-issuance.duration}")
    @Setter
    private Integer duration;

    public Long calculateFine(Date dueDate){

        Date currentDate = new Date();
        long timeDifference = currentDate.getTime() - dueDate.getTime(); // epoch time diff

        if(timeDifference < 0){
            return 0L;
        }

        long numberOfDaysPassed = TimeUnit.MILLISECONDS.toDays(timeDifference);
        return (numberOfDaysPassed -  maxThreshold) * finePerDay * 100; // in paise // lowest denomination
    }

    public Date calculateDueDate(){
        LocalDate localDate = LocalDate.now();
        LocalDate dueDate = localDate.plusDays(duration);

        java.time.LocalDateTime localDateTime = dueDate.atStartOfDay();

        // 2. Interpret the LocalDateTime in a specific time zone to get a ZonedDateTime
        java.time.ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        // 3. Convert the ZonedDateTime to an Instant (a moment in UTC)
        java.time.Instant instant = zonedDateTime.toInstant();

        // 4. Convert the Instant to a java.util.Date
        return Date.from(instant);
    }

}
