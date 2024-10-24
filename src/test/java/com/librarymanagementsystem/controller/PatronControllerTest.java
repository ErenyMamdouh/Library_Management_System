package com.librarymanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagementsystem.dto.PatronDto;
import com.librarymanagementsystem.model.Patron;
import com.librarymanagementsystem.service.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PatronController.class)
public class PatronControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    private PatronDto patronDto;
    private Patron savedPatron;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();

        patronDto=new PatronDto();
        patronDto.setFullName("Ereny Mamdouh");
        patronDto.setAddress("cairo");
        patronDto.setEmail("ereny@gmail.com");
        patronDto.setPhone("0120344");

        savedPatron= new Patron();
        savedPatron.setPatronId(1L);
        savedPatron.setFullName(patronDto.getFullName());
        savedPatron.setAddress(patronDto.getAddress());
        savedPatron.setEmail(patronDto.getEmail());
        savedPatron.setPhone(patronDto.getPhone());

    }

    @Test
    void addPatronShouldReturnCreatedPatron() throws Exception{

        doNothing().when(patronService).savePatron(any(PatronDto.class));

        mockMvc.perform(post("/api/patrons/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patronDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Patron has been successfully added!"));

    }

    @Test
    void getPatronByIdShouldReturnPatronDtoWhenFound() throws Exception{

        when(patronService.getPatronbyId(anyLong())).thenReturn(Optional.of(patronDto));

        mockMvc.perform(get("/api/patrons/get/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patronId").value(patronDto.getPatronId()))
                .andExpect(jsonPath("$.address").value(patronDto.getAddress()))
                .andExpect(jsonPath("$.phone").value(patronDto.getPhone()))
                .andExpect(jsonPath("$.fullName").value(patronDto.getFullName()))
                .andExpect(jsonPath("$.email").value(patronDto.getEmail()));

    }

    @Test
    void getPatronByIdShouldThrowPatronExceptionWhenNotFound() throws Exception{

        when(patronService.getPatronbyId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/patrons/get/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patron not found with id:1"))
                .andExpect(jsonPath("$.httpStatus").value("NOT_FOUND"));
    }

    @Test
    void getAllPatronsShouldReturnListOfPatronDtos() throws Exception{

        when(patronService.getAllPatrons()).thenReturn(List.of(patronDto));

        mockMvc.perform(get("/api/patrons/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].fullName").value("Ereny Mamdouh"))
                .andExpect(jsonPath("$[0].email").value("ereny@gmail.com"))
                .andExpect(jsonPath("$[0].address").value("cairo"))
                .andExpect(jsonPath("$[0].phone").value("0120344"));

    }

    @Test
    void updatePatronByIdShouldReturnUpdatedPatron() throws Exception{

        PatronDto updatedPatronDto = new PatronDto();
        updatedPatronDto.setFullName("Updated Name");
        updatedPatronDto.setAddress("Updated Address");
        updatedPatronDto.setEmail("updated@gmail.com");
        updatedPatronDto.setPhone("0987654321");


        Patron updatedPatron = new Patron();
        updatedPatron.setPatronId(1L);
        updatedPatron.setFullName(updatedPatronDto.getFullName());
        updatedPatron.setAddress(updatedPatronDto.getAddress());
        updatedPatron.setEmail(updatedPatronDto.getEmail());
        updatedPatron.setPhone(updatedPatronDto.getPhone());


        when(patronService.getPatronbyId(anyLong())).thenReturn(Optional.of(patronDto));
        when(patronService.updatePatronbyId(any(PatronDto.class), anyLong())).thenReturn(updatedPatron);


        mockMvc.perform(put("/api/patrons/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPatronDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patronId").value(1L))
                .andExpect(jsonPath("$.fullName").value("Updated Name"))
                .andExpect(jsonPath("$.address").value("Updated Address"))
                .andExpect(jsonPath("$.email").value("updated@gmail.com"))
                .andExpect(jsonPath("$.phone").value("0987654321"));
    }

    @Test
    void updatePatronByIdShouldThrowPatronExceptionWhenNotFound() throws Exception{

        when(patronService.getPatronbyId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/patrons/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patronDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("patron not found with id:1"))
                .andExpect(jsonPath("$.httpStatus").value("NOT_FOUND"));

    }

    @Test
    void deletePatronByIdShouldReturnSuccessMessage() throws Exception{

        when(patronService.getPatronbyId(anyLong())).thenReturn(Optional.of(patronDto));

        doNothing().when(patronService).deletePatronbyID(1L);

        mockMvc.perform(delete("/api/patrons/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Patron with id: 1 is deleted successfully!"));

    }

    @Test
    void deletePatronByIdShouldReturnNotFound() throws Exception {
        Long patronId = 1L;

        when(patronService.getPatronbyId(patronId)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/patrons/delete/{patronId}", patronId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patron not found with id:{} " + patronId+" to delete"))
                .andExpect(jsonPath("$.httpStatus").value("NOT_FOUND"));
    }


}
