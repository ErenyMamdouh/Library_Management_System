package com.librarymanagementsystem.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagementsystem.dto.BookDto;
import com.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BoockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private BookDto bookDto;
    private Book savedBook;
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        bookDto = new BookDto();
        bookDto.setTitle("Sample Book");
        bookDto.setAuthor("Author Name");
        bookDto.setIsbn("1234567890");

        savedBook = new Book();
        savedBook.setBookId(1L);
        savedBook.setTitle(bookDto.getTitle());
        savedBook.setAuthor(bookDto.getAuthor());
        savedBook.setIsbn(bookDto.getIsbn());
    }

    @Test
    void addBookShouldReturnCreatedBook() throws Exception{

        when(bookService.saveBook(any(BookDto.class))).thenReturn(savedBook);

        mockMvc.perform(post("/api/books/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookId").value(savedBook.getBookId()))
                .andExpect(jsonPath("$.title").value(savedBook.getTitle()))
                .andExpect(jsonPath("$.author").value(savedBook.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(savedBook.getIsbn()));

    }

    @Test
    void getBookByIdShouldReturnBookDtoWhenFound() throws Exception{

        when(bookService.getBookbyId(anyLong())).thenReturn(Optional.of(bookDto));

        mockMvc.perform(get("/api/books/get/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(bookDto.getBookId()))
                .andExpect(jsonPath("$.title").value(bookDto.getTitle()))
                .andExpect(jsonPath("$.author").value(bookDto.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(bookDto.getIsbn()));

    }

    @Test
    void getBookByIdShouldThrowBookExceptionWhenNotFound() throws Exception{

        when(bookService.getBookbyId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/get/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book not found with id: 1"))
                .andExpect(jsonPath("$.httpStatus").value("NOT_FOUND"));
    }

    @Test
    void getAllBooksShouldReturnListOfBookDtos() throws Exception{

        when(bookService.getAllBooks()).thenReturn(List.of(bookDto));
        mockMvc.perform(get("/api/books/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Sample Book"))
                .andExpect(jsonPath("$[0].author").value("Author Name"))
                .andExpect(jsonPath("$[0].isbn").value("1234567890"));
    }


    @Test
    void updateBookByIdShouldReturnUpdatedBook() throws Exception{

        BookDto updatedBookDto = new BookDto();
        updatedBookDto.setTitle("Updated Title");
        updatedBookDto.setAuthor("Updated Author");
        updatedBookDto.setIsbn("0987654321");

        Book updatedBook = new Book();
        updatedBook.setBookId(1L);
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setIsbn("0987654321");

        when(bookService.getBookbyId(anyLong())).thenReturn(Optional.of(bookDto));
        when(bookService.updateBookbyId(any(BookDto.class), anyLong())).thenReturn(updatedBook);

        mockMvc.perform(put("/api/books/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBookDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(updatedBook.getBookId()))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.author").value("Updated Author"))
                .andExpect(jsonPath("$.isbn").value("0987654321"));
    }

    @Test
    void UpdateBookByIdShouldThrowBookExceptionWhenNotFound() throws Exception{

        when(bookService.getBookbyId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/get/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book not found with id: 1"))
                .andExpect(jsonPath("$.httpStatus").value("NOT_FOUND"));
    }

    @Test
    void deleteBookByIdShouldReturnSuccessMessage() throws Exception{

        when(bookService.getBookbyId(1L)).thenReturn(Optional.of(bookDto));
        doNothing().when(bookService).deleteBookbyId(1L);

        mockMvc.perform(delete("/api/books/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Book with id 1 is deleted successfully!"));
    }

    @Test
    void deleteBookByIdShouldReturnNotFound() throws Exception{
        Long bookId=1L;

        when(bookService.getBookbyId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/books/delete/{bookId}", bookId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book not found with id: " + bookId+" to delete"))
                .andExpect(jsonPath("$.httpStatus").value("NOT_FOUND"));

    }


}
