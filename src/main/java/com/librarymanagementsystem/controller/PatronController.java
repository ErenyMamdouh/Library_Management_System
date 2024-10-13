package com.librarymanagementsystem.controller;

import com.librarymanagementsystem.dto.PatronDto;
import com.librarymanagementsystem.entity.Patron;
import com.librarymanagementsystem.exception.PatronException;
import com.librarymanagementsystem.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/patrons")
@RequiredArgsConstructor
public class PatronController {

    private final PatronService patronService;

    @PostMapping("/add")
    public ResponseEntity<String> addPatron(@RequestBody PatronDto patronDto){

        patronService.savePatron(patronDto);

        return  ResponseEntity.status(HttpStatus.CREATED).body("Patron has been successfully added!");
    }

    @GetMapping("/get/{patronId}")
    public ResponseEntity<PatronDto> getPatronbyId(@PathVariable Long patronId){

        return patronService.getPatronbyId(patronId)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new PatronException("Patron not found with id:"+patronId));

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PatronDto>> getAllPatrons(){

        List<PatronDto> patronDtos=patronService.getAllPatrons();
        return ResponseEntity.ok(patronDtos);
    }

    @PutMapping("/update/{patronId}")
    public ResponseEntity<Patron> updatePatronbyId(@RequestBody PatronDto patronDto, @PathVariable Long patronId){

        patronService.getPatronbyId(patronId)
                .orElseThrow(()-> new PatronException("patron not found with id:" + patronId));

        Patron updatedPatron= patronService.updatePatronbyId(patronDto,patronId);

        return ResponseEntity.ok(updatedPatron);
    }

    @DeleteMapping("/delete/{patronId}")
    public ResponseEntity<String> deletePatronbyId(@PathVariable Long patronId){

        patronService.getPatronbyId(patronId)
                .orElseThrow(()-> new PatronException("Patron not found with id:{} " + patronId+" to delete" ));

        patronService.deletePatronbyID(patronId);
        return ResponseEntity.ok("Patron with id: " + patronId+" is deleted successfully!");
    }



}
