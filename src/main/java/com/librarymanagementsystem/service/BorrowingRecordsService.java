package com.librarymanagementsystem.service;

import com.librarymanagementsystem.dao.BookRepo;
import com.librarymanagementsystem.dao.BorrowingRecordsRepo;
import com.librarymanagementsystem.dao.PatronRepo;
import com.librarymanagementsystem.dto.BorrowingRecordsDto;
import com.librarymanagementsystem.dto.mapstruct.BorrowingRecordsMapper;
import com.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.model.BorrowingRecords;
import com.librarymanagementsystem.model.Patron;
import com.librarymanagementsystem.exception.BookException;
import com.librarymanagementsystem.exception.BorrowingRecordException;
import com.librarymanagementsystem.exception.PatronException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@Service
@RequiredArgsConstructor
public class BorrowingRecordsService {

    private final BorrowingRecordsRepo borrowingRecordsRepo;
    private final PatronRepo patronRepo;
    private final BookRepo bookRepo;
    private final BorrowingRecordsMapper borrowingRecordsMapper;
    private static final Logger logger= LoggerFactory.getLogger(BorrowingRecordsService.class);

    public BorrowingRecordsDto borrowBook(Long bookId,Long patronId){

        logger.info("Borrowing book with ID: {} by patron with ID: {}", bookId, patronId);

        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new BookException("Book not found with id: " + bookId));

        Patron patron = patronRepo.findById(patronId)
                .orElseThrow(() -> new PatronException("Patron not found with id: " + patronId));

        BorrowingRecords borrowingRecord = new BorrowingRecords();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate( LocalDate.now());

        BorrowingRecords savedRecord = borrowingRecordsRepo.save(borrowingRecord);
        return borrowingRecordsMapper.toDto(savedRecord);

    }

    public BorrowingRecordsDto returnBorrowBook(Long bookId,Long patronId){

        logger.info("Returning book with ID: {} by patron with ID: {}", bookId, patronId);

        BorrowingRecords borrowingRecord=borrowingRecordsRepo.findByBook_BookIdAndPatron_PatronId(bookId,patronId)
                .orElseThrow(()-> new BorrowingRecordException("No borrowing record found for bookId: " + bookId + " and patronId: " + patronId));

        if(borrowingRecord.getReturnDate()!=null){
            throw new BorrowingRecordException("Book has already been returned.");
        }

        borrowingRecord.setReturnDate(LocalDate.now());

        BorrowingRecords updatedRecord = borrowingRecordsRepo.save(borrowingRecord);
        return borrowingRecordsMapper.toDto(updatedRecord);
    }


}
