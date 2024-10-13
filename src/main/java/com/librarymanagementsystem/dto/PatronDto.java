package com.librarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatronDto {

    private Long patronId;
    private String fullName;
    private String email;
    private String phone;
    private String address;

}
