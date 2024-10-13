package com.librarymanagementsystem.controller;



import com.librarymanagementsystem.dto.BorrowingRecordsDto;
import com.librarymanagementsystem.service.BorrowingRecordsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BorrowingRecordsController {

    private final BorrowingRecordsService borrowingRecordsService;



    @Operation(summary = "Allow a patron to borrow a book ")
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordsDto> borrowBook(@PathVariable Long bookId,@PathVariable Long patronId){

        BorrowingRecordsDto boorowDto = borrowingRecordsService.borrowBook(bookId,patronId);
        return ResponseEntity.status(HttpStatus.CREATED).body(boorowDto);

    }

    @Operation(summary = "Record the return of a borrowed book by a patron")
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordsDto> returnBook(@PathVariable Long bookId,@PathVariable Long patronId){

        BorrowingRecordsDto returnedRecord  = borrowingRecordsService.returnBorrowBook(bookId,patronId);
        return ResponseEntity.ok(returnedRecord);


    }



    }
