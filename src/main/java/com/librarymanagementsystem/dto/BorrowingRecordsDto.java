package com.librarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingRecordsDto {

    private Long recordId;
    private LocalDate borrowDate;
    private LocalDate returnDate;

}
