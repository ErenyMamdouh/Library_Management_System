package com.librarymanagementsystem.service;

import com.librarymanagementsystem.dao.PatronRepo;
import com.librarymanagementsystem.dto.PatronDto;
import com.librarymanagementsystem.dto.mapstruct.PatronMapper;
import com.librarymanagementsystem.entity.Patron;
import com.librarymanagementsystem.exception.PatronException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatronServiceTest {

    @Mock
    private PatronRepo repo;

    @Mock
    private PatronMapper patronMapper;

    @InjectMocks
    private PatronService patronService;

    private PatronDto patronDto;
    private Patron patron;

    @BeforeEach
    void setUp() {
        patronDto = new PatronDto();
        patronDto.setEmail("ereny@gmail.com");
        patronDto.setAddress("cairo");
        patronDto.setFullName("ereny");
        patronDto.setPhone("012345");

        patron = new Patron();
        patron.setEmail("ereny@gmail.com");
        patron.setAddress("cairo");
        patron.setFullName("ereny");
        patron.setPhone("012345");
    }

    @Test
    void testSavePatron(){

        when(patronMapper.mapToEntity(patronDto)).thenReturn(patron);
        when(repo.save(any(Patron.class))).thenReturn(patron);

        patronService.savePatron(patronDto);

        assertThat(patron.getFullName()).isEqualTo("ereny");
        assertThat(patron.getEmail()).isEqualTo("ereny@gmail.com");
        assertThat(patron.getAddress()).isEqualTo("cairo");
        assertThat(patron.getPhone()).isEqualTo("012345");

    }

    @Test
    void testFindpatronbyIdshouldReturnPatron(){

        patronDto.setPatronId(1L);
        patronDto.setPatronId(1L);

        when(repo.findById(1L)).thenReturn(Optional.of(patron));
        when(patronMapper.mapToDto(patron)).thenReturn(patronDto);

        Optional<PatronDto> updatedPatron= patronService.getPatronbyId(1L);

        assertThat(updatedPatron.get().getPatronId()).isEqualTo(1L);
        assertThat(updatedPatron.get().getEmail()).isEqualTo("ereny@gmail.com");
        assertThat(updatedPatron.get().getAddress()).isEqualTo("cairo");
    }

    @Test
    void findPatronbyIdShouldThrowExceptionWhenPatronNotFound(){

        Long patronId=1L;

        doThrow(new PatronException("Patron not found with ID: " + patronId)).when(repo).findById(patronId);

        assertThatThrownBy(()->patronService.getPatronbyId(patronId))
                .isInstanceOf(PatronException.class)
                .hasMessage("Patron not found with ID: " + patronId);


    }

    @Test
    void testGetAllPatronsShouldReturnAllPatrons(){

        PatronDto patronDto1 = new PatronDto();
        patronDto1.setFullName("ereny");

        Patron patron1= new Patron();
        patron1.setFullName("ereny");

        PatronDto patronDto2 = new PatronDto();
        patronDto2.setFullName("mamdouh");

        Patron patron2= new Patron();
        patron2.setFullName("mamdouh");

        when(repo.findAll()).thenReturn(Arrays.asList(patron1,patron2));
        when(patronMapper.mapToDto(patron1)).thenReturn(patronDto1);
        when(patronMapper.mapToDto(patron2)).thenReturn(patronDto2);

        List<PatronDto> result=patronService.getAllPatrons();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getFullName()).isEqualTo("ereny");
        assertThat(result.get(1).getFullName()).isEqualTo("mamdouh");
    }

    @Test
    void testUpdatePatronbyIdShouldReturnUpdatedPatron(){
        patronDto.setAddress("Old address");
        patronDto.setPatronId(1L);

        Patron existingpatron= new Patron();
        existingpatron.setAddress("Old address");
        existingpatron.setPatronId(1L);

        Patron updatedPatron= new Patron();
        updatedPatron.setAddress("New address");

        when(repo.save(any(Patron.class))).thenReturn(updatedPatron);
        when(patronMapper.mapToEntity(patronDto)).thenReturn(updatedPatron);

        Patron result = patronService.updatePatronbyId(patronDto,1L);

        assertThat(result).isNotNull();
        assertThat(result.getAddress()).isEqualTo("New address");
    }
    @Test
    void updatePatronbyIdShouldThrowExceptionWhenPatronNotFound() {

        Long patronId = 1L;

        doThrow(new PatronException("Patron not found to update with ID: " + patronId)).when(repo).findById(patronId);

        assertThatThrownBy(() -> patronService.getPatronbyId(patronId))
                .isInstanceOf(PatronException.class)
                .hasMessage("Patron not found to update with ID: " + patronId);
    }

    @Test
    void testdeletePatronbyId(){

        Long PatronId=1L;

        patronService.deletePatronbyID(PatronId);

        verify(repo).deleteById(PatronId);
    }

    @Test
    void deletePatronbyIdShouldThrowExceptionWhenPatronNotFound(){

        Long patronId=1L;

        doThrow(new PatronException("Patron not found to delete with ID: " + patronId)).when(repo).deleteById(patronId);

        assertThatThrownBy(()->patronService.deletePatronbyID(patronId))
                .isInstanceOf(PatronException.class)
                .hasMessage("Patron not found to delete with ID: " + patronId);
    }

}
