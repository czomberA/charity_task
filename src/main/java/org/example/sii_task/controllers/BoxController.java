package org.example.sii_task.controllers;

import jakarta.validation.Valid;
import org.example.sii_task.models.collected.CollectedDTO;
import org.example.sii_task.models.charityBox.*;
import org.example.sii_task.services.CharityBoxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/box")
public class BoxController {
    private final CharityBoxService charityBoxService;
    public BoxController(CharityBoxService charityBoxService) {
        this.charityBoxService = charityBoxService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCharityBox(@PathVariable String id, @Valid @RequestBody CharityBoxAssignDTO charityBoxAssignDTO) {
        CharityBoxAssignedDTO assignedDTO = charityBoxService.assign(id, charityBoxAssignDTO);
            return new ResponseEntity<>(assignedDTO, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<?> getAllBoxes() {
        List<CharityBoxReturnDTO> boxes = charityBoxService.getAllBoxes();
        return ResponseEntity.ok(boxes);
    }

    @PostMapping
    public ResponseEntity<?> addBox(@Valid  @RequestBody CharityBoxCreateDTO charityBoxCreateDTO) {
        CharityBox charityBox = new CharityBox();
        charityBox.setIdentifier(charityBoxCreateDTO.getIdentifier());
        return new ResponseEntity<>(charityBoxService.createBox(charityBox), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBox(@PathVariable String id) {
            charityBoxService.removeBox(id);
        return  ResponseEntity.noContent().build();
    }



    @PutMapping("/donate")
    public ResponseEntity<?> donate(@Valid @RequestBody CollectedDTO collectedDTO) {
        charityBoxService.donate(collectedDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/empty/{id}")
    public ResponseEntity<?> emptyBox(@PathVariable String id) {
        charityBoxService.empty(id);
        return ResponseEntity.noContent().build();
    }
}
