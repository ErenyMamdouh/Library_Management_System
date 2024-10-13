package com.librarymanagementsystem.service;


import com.librarymanagementsystem.dao.BookRepo;
import com.librarymanagementsystem.dao.BorrowingRecordsRepo;
import com.librarymanagementsystem.dao.PatronRepo;
import com.librarymanagementsystem.dto.BorrowingRecordsDto;
import com.librarymanagementsystem.dto.mapstruct.BorrowingRecordsMapper;
import com.librarymanagementsystem.entity.Book;
import com.librarymanagementsystem.entity.BorrowingRecords;
import com.librarymanagementsystem.entity.Patron;
import com.librarymanagementsystem.exception.BookException;
import com.librarymanagementsystem.exception.BorrowingRecordException;
import com.librarymanagementsystem.exception.PatronException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordsServiceTest {

    @Mock
    private BorrowingRecordsRepo borrowRepo;

    @Mock
    private PatronRepo PatronRepo;

    @Mock
    private BookRepo bookRepo;

    @Mock
    private BorrowingRecordsMapper borrowingMapper;

    @InjectMocks
    private BorrowingRecordsService borrowingService;

    private Long bookId;
    private Long patronId;
    private Book book;
    private Patron patron;
    private BorrowingRecords borrowingRecord;
    private BorrowingRecordsDto borrowingDto;

    @BeforeEach
    void setUp() {
        bookId = 1L;
        patronId = 1L;

        book = new Book();
        book.setBookId(bookId);
        book.setTitle("Sample Book");

        patron = new Patron();
        patron.setPatronId(patronId);
        patron.setFullName("John Doe");

        borrowingRecord = new BorrowingRecords();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());

        borrowingDto = new BorrowingRecordsDto();
    }

    @Test
    void testBorrowBookShouldReturnBorrowRecord(){

        when(PatronRepo.findById(1L)).thenReturn(Optional.of(patron));
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(borrowRepo.save(borrowingRecord)).thenReturn(borrowingRecord);
        when(borrowingMapper.toDto(borrowingRecord)).thenReturn(borrowingDto);

        BorrowingRecordsDto result= borrowingService.borrowBook(bookId,patronId);

        assertThat(result).isNotNull();
    }

    @Test
    void borrowBookShouldThrowPatronExceptionWhenPatronNotFound(){

        when(bookRepo.findById(bookId)).thenReturn(Optional.of(book));
        when(PatronRepo.findById(patronId)).thenReturn(Optional.empty());

        assertThrows(PatronException.class,()-> borrowingService.borrowBook(bookId,patronId));
    }

    @Test
    void borrowBookShouldThrowPatronExceptionWhenbookNotFound(){

        when(bookRepo.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookException.class,()-> borrowingService.borrowBook(bookId,patronId));
    }

    @Test
    void testReturnBorrowedBookShouldReturnBorrowRecord(){

        when(borrowRepo.findByBook_BookIdAndPatron_PatronId(bookId,patronId))
                .thenReturn(Optional.of(borrowingRecord));
        when(borrowRepo.save(borrowingRecord)).thenReturn(borrowingRecord);
        when(borrowingMapper.toDto(borrowingRecord)).thenReturn(borrowingDto);

        BorrowingRecordsDto result = borrowingService.returnBorrowBook(bookId,patronId);

        assertThat(result).isNotNull();

    }

    @Test
    void testReturnBorrowBookThrowsExceptionWhenAlreadyReturned() {

        borrowingRecord.setReturnDate(LocalDate.now());
        when(borrowRepo.findByBook_BookIdAndPatron_PatronId(bookId, patronId))
                .thenReturn(Optional.of(borrowingRecord));

        assertThatThrownBy(() -> borrowingService.returnBorrowBook(bookId, patronId))
                .isInstanceOf(BorrowingRecordException.class)
                .hasMessageContaining("Book has already been returned.");

    }

    @Test
    void testReturnBorrowBookThrowsExceptionWhenNoRecordFound() {

        when(borrowRepo.findByBook_BookIdAndPatron_PatronId(bookId, patronId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> borrowingService.returnBorrowBook(bookId, patronId))
                .isInstanceOf(BorrowingRecordException.class)
                .hasMessageContaining("No borrowing record found for bookId: " + bookId + " and patronId: " + patronId);
    }

    }
