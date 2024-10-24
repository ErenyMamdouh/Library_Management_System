package com.librarymanagementsystem.service;


import com.librarymanagementsystem.dao.BookRepo;
import com.librarymanagementsystem.dto.BookDto;
import com.librarymanagementsystem.dto.mapstruct.BookMapper;
import com.librarymanagementsystem.model.Book;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepo bookRepo;
    private final BookMapper bookMapper;
    private static final Logger logger= LoggerFactory.getLogger(BookService.class);

    @Transactional
    public Book saveBook(BookDto bookDto){

        Book book= bookMapper.mapToEntity(bookDto);

        logger.info("Received Book DTO: {}", bookDto);
        logger.info("Mapped Book Entity: {}", book);
         return bookRepo.save(book);
    }

    @Cacheable(value = "books",key = "#bookId")
    public Optional<BookDto> getBookbyId(Long bookId){

        logger.info(" Fetching book with ID: {}",bookId);

        return bookRepo.findById(bookId).map(bookMapper:: mapToDto);
    }

    @CacheEvict(value = "books",key = "#root.methodName",allEntries = true)
    public List<BookDto> getAllBooks(){

        logger.info(" Fetching all books  ");

        return bookRepo.findAll()
                .stream()
                .map(bookMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "books", key = "#root.methodName")
    public Book updateBookbyId( BookDto bookDto, Long bookId ){

        logger.info(" updating book with ID: {}",bookId);

        Book updatedBook = bookMapper.mapToEntity(bookDto);
        updatedBook.setBookId(bookId);
        return bookRepo.save(updatedBook);
    }

    @Transactional
    @CacheEvict(value = "books",key = "#bookId",allEntries = true)
    public void deleteBookbyId(Long bookId){

        logger.info(" deleting book with ID: {}",bookId);

        bookRepo.deleteById(bookId);
    }



}
