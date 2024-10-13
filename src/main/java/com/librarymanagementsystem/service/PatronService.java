package com.librarymanagementsystem.service;


import com.librarymanagementsystem.dao.PatronRepo;
import com.librarymanagementsystem.dto.PatronDto;
import com.librarymanagementsystem.dto.mapstruct.PatronMapper;
import com.librarymanagementsystem.entity.Patron;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatronService {

    private final PatronRepo patronRepo;
    private final PatronMapper patronMapper;
    private static final Logger logger= LoggerFactory.getLogger(PatronService.class);


    public void savePatron(PatronDto patronDto){

        Patron patron= patronMapper.mapToEntity(patronDto);
        logger.info("Saving new patron: {}",patron);
        patronRepo.save(patron);

    }

    public Optional<PatronDto> getPatronbyId(Long patronId){
        logger.info(" Fetching patron with ID: {}",patronId);

        return patronRepo.findById(patronId).map(patronMapper::mapToDto);
    }

    public List<PatronDto> getAllPatrons(){

        logger.info(" Fetching all patrons");

        return patronRepo.findAll()
                .stream()
                .map(patronMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public Patron updatePatronbyId(PatronDto patronDto,Long patronId){

        Patron updatedPatron= patronMapper.mapToEntity(patronDto);
        updatedPatron.setPatronId(patronId);
        return patronRepo.save(updatedPatron);

    }

    public void deletePatronbyID(Long patronId){

        patronRepo.deleteById(patronId);
    }
}
