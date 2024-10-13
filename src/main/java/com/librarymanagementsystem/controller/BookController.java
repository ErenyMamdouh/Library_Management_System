package com.librarymanagementsystem.controller;


import com.librarymanagementsystem.dto.BookDto;
import com.librarymanagementsystem.entity.Book;
import com.librarymanagementsystem.exception.BookException;
import com.librarymanagementsystem.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
public class BookController {


    private final BookService bookService;

    @Operation(summary = "add a new book")
    @PostMapping("/add")
    public ResponseEntity<Book> addbook(@RequestBody @NotNull BookDto bookDto){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.saveBook(bookDto));
    }

    @GetMapping("/get/{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long bookId){

        return bookService.getBookbyId(bookId)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new BookException("Book not found with id: " + bookId));

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BookDto>> getAllBooks(){

        List<BookDto> bookDtos= bookService.getAllBooks();

        return ResponseEntity.ok(bookDtos);
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<Book> updateBookbyId(@RequestBody BookDto bookDto, @PathVariable Long bookId){

         bookService.getBookbyId(bookId)
                .orElseThrow(()-> new BookException("Book not found with id: " + bookId));


        Book updatedbook= bookService.updateBookbyId(bookDto,bookId);

        return ResponseEntity.ok(updatedbook);
    }

    @DeleteMapping("delete/{bookId}")
    public ResponseEntity<String> deleteBookbyId(@PathVariable Long bookId){

        bookService.getBookbyId(bookId)
                .orElseThrow(()-> new BookException("Book not found with id: " + bookId+" to delete"));

        bookService.deleteBookbyId(bookId);

        return ResponseEntity.ok("Book with id "+bookId+" is deleted successfully!");

    }








}
