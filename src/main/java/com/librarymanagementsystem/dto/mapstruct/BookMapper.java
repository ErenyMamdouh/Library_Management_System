package com.librarymanagementsystem.dto.mapstruct;

import com.librarymanagementsystem.dto.BookDto;
import com.librarymanagementsystem.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    // map entity to dto
    BookDto mapToDto(Book entity);

    // map dto to entity
    Book mapToEntity(BookDto bookDto);

}
