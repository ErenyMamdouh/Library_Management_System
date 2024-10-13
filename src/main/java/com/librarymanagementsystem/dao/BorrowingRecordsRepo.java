package com.librarymanagementsystem.dao;

import com.librarymanagementsystem.entity.BorrowingRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordsRepo extends JpaRepository<BorrowingRecords, Long> {

//    Optional<BorrowingRecords> findByBookIdAndPatronId(Long bookId, Long patronId);
      Optional<BorrowingRecords> findByBook_BookIdAndPatron_PatronId(Long bookId, Long patronId);
}




