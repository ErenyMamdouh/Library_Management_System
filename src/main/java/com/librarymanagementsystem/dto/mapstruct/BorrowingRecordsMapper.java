package com.librarymanagementsystem.dto.mapstruct;

import com.librarymanagementsystem.dto.BorrowingRecordsDto;
import com.librarymanagementsystem.entity.BorrowingRecords;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BorrowingRecordsMapper {


    // Map entity to DTO
    BorrowingRecordsDto toDto(BorrowingRecords entity);
}
