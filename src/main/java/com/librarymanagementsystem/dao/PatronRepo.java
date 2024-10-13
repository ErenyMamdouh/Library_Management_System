package com.librarymanagementsystem.dao;

import com.librarymanagementsystem.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepo extends JpaRepository<Patron, Long> {
}
