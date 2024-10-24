package com.librarymanagementsystem.dto.mapstruct;

import com.librarymanagementsystem.dto.PatronDto;
import com.librarymanagementsystem.model.Patron;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatronMapper {

    // map entity to dto
    PatronDto mapToDto(Patron entity);

    // map dto to entity
    Patron mapToEntity(PatronDto patronDto);
}
