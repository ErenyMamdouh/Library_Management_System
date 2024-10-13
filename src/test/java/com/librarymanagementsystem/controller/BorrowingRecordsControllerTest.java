package com.librarymanagementsystem.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagementsystem.dto.BorrowingRecordsDto;
import com.librarymanagementsystem.service.BorrowingRecordsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BorrowingRecordsController.class)
public class BorrowingRecordsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingRecordsService borrowingRecordsService;

    private BorrowingRecordsDto borrowingRecordsDto;

    private ObjectMapper objectMapper;



    @BeforeEach
    void setUp(){

        objectMapper = new ObjectMapper();

        borrowingRecordsDto = new BorrowingRecordsDto();
        borrowingRecordsDto.setRecordId(1L);
        borrowingRecordsDto.setBorrowDate(LocalDate.of(2024, 1, 1));
        borrowingRecordsDto.setReturnDate(null);


    }

    @Test
    void BorrowBookShouldReturnBorrowRecord() throws Exception{

        when(borrowingRecordsService.borrowBook(anyLong(), anyLong())).thenReturn(borrowingRecordsDto);

        mockMvc.perform(post("/api/borrow/1/patron/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.recordId").value(1L))
                .andExpect(jsonPath("$.borrowDate").exists())
                .andExpect(jsonPath("$.returnDate").isEmpty());

    }

    @Test
    void testReturnBookShouldReturnUpdatedRecord() throws Exception {

        borrowingRecordsDto.setReturnDate(LocalDate.now());

        when(borrowingRecordsService.returnBorrowBook(anyLong(), anyLong())).thenReturn(borrowingRecordsDto);

        mockMvc.perform(put("/api/return/1/patron/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recordId").value(1L))
                .andExpect(jsonPath("$.borrowDate").value("2024-01-01"))
                .andExpect(jsonPath("$.returnDate").exists());
    }




}
