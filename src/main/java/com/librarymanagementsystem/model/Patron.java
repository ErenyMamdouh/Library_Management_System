package com.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long patronId;
    private String fullName;
    private String email;
    private String phone;
    private String address;

//    @OneToMany
//    private List<Book> book;
//
//    @OneToMany
//    private List<BorrowingRecords> borrowingRecords;
}
