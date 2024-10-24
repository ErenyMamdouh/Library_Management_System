package com.librarymanagementsystem.service;


import com.librarymanagementsystem.dao.BookRepo;
import com.librarymanagementsystem.dto.BookDto;
import com.librarymanagementsystem.dto.mapstruct.BookMapper;
import com.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.exception.BookException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    private BookDto bookDto;
    private Book book;

    @BeforeEach
    void setUp(){
        bookDto = new BookDto();
        bookDto.setAuthor("ereny");
        bookDto.setTitle("java");
        bookDto.setIsbn("1234567");

        book = new Book();
        book.setAuthor("ereny");
        book.setTitle("java");
        book.setIsbn("1234567");

    }

    @Test
    void testSaveBook(){

        when(bookMapper.mapToEntity(bookDto)).thenReturn(book);
        when(bookRepo.save(any(Book.class))).thenReturn(book);

        Book savedBook=bookService.saveBook(bookDto);

        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getAuthor()).isEqualTo("ereny");
        assertThat(savedBook.getTitle()).isEqualTo("java");
        assertThat(savedBook.getIsbn()).isEqualTo("1234567");

    }

    @Test
    void testFindBookbyIdshouldReturnbook(){

        book.setBookId(1L);
        bookDto.setBookId(1L);

        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.mapToDto(book)).thenReturn(bookDto);

        Optional<BookDto> result=bookService.getBookbyId(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getBookId()).isEqualTo(1L);
        assertThat(result.get().getTitle()).isEqualTo("java");

    }

    @Test
    void testfindBookbyIdShouldThrowExceptionWhenBookNotFound(){

        Long bookId=1L;

        doThrow(new BookException("Book not found with ID: " + bookId)).when(bookRepo).findById(bookId);

        assertThatThrownBy(()->bookService.getBookbyId(bookId))
                .isInstanceOf(BookException.class)
                .hasMessage("Book not found with ID: " + bookId);


    }

    @Test
    void testGetAllBooksShouldReturnAllBooks(){

        Book book1= new Book();
        book1.setTitle("spring boot");

        Book book2= new Book();
        book2.setTitle("spring ");

        BookDto bookDto1=new BookDto();
        bookDto1.setTitle("spring boot");

        BookDto bookDto2=new BookDto();
        bookDto2.setTitle("spring ");

        when(bookRepo.findAll()).thenReturn(Arrays.asList(book1,book2));
        when(bookMapper.mapToDto(book1)).thenReturn(bookDto1);
        when(bookMapper.mapToDto(book2)).thenReturn(bookDto2);

        List<BookDto> result= bookService.getAllBooks();


        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("spring boot");
        assertThat(result.get(1).getTitle()).isEqualTo("spring ");

    }

    @Test
    void testUpdatebookbyIdShouldReturnUpdatedBook(){

        BookDto bookDto=new BookDto();
        bookDto.setBookId(1L);
        bookDto.setTitle("new Title");

        book.setBookId(1L);
        book.setTitle("Old Title");

        Book updatedBook= new Book();
        updatedBook.setBookId(1L);
        updatedBook.setTitle("new Title");

        when(bookMapper.mapToEntity(bookDto)).thenReturn(updatedBook);
        when(bookRepo.save(any(Book.class))).thenReturn(updatedBook);

        Book result=bookService.updateBookbyId(bookDto,1L);

        assertThat(result).isNotNull();
        assertThat(result.getBookId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("new Title");
    }
    @Test
    void updateBookbyIdShouldThrowExceptionWhenBookNotFound(){

        Long bookId=1L;

        doThrow(new BookException("Book not found with ID: " + bookId)).when(bookRepo).findById(bookId);

        assertThatThrownBy(()->bookService.getBookbyId(bookId))
                .isInstanceOf(BookException.class)
                .hasMessage("Book not found with ID: " + bookId);
    }

    @Test
    void testdeleteBookbyId(){

        Long bookId = 1L;

        bookService.deleteBookbyId(bookId);

        verify(bookRepo).deleteById(bookId);
    }

    @Test
    void deleteBookbyIdShouldThrowExceptionWhenBookNotFound() {

        Long bookId=1L;

        doThrow(new BookException("Book not found with ID: " + bookId)).when(bookRepo).deleteById(bookId);

        assertThatThrownBy(()->bookService.deleteBookbyId(bookId))
                .isInstanceOf(BookException.class)
                .hasMessage("Book not found with ID: " + bookId);
    }









}
