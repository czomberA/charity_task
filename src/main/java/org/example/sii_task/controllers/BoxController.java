package org.example.sii_task.controllers;

import org.example.sii_task.models.collected.CollectedDTO;
import org.example.sii_task.models.charityBox.*;
import org.example.sii_task.models.fundraiser.FundraiserBoxAssignedDTO;
import org.example.sii_task.services.CharityBoxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/box")
public class BoxController {
    private final CharityBoxService charityBoxService;
    public BoxController(CharityBoxService charityBoxService) {
        this.charityBoxService = charityBoxService;
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCharityBox(@PathVariable String id, @RequestBody CharityBoxAssignDTO charityBoxAssignDTO) {
        try {
            Optional<CharityBox> optionalBox = charityBoxService.findById(id);
            if (optionalBox.isEmpty()) {
                return new ResponseEntity<>("CharityBox not found", HttpStatus.NOT_FOUND);
            }

            CharityBox existingBox = optionalBox.get();

            if (!charityBoxService.canAssign(id)) {
                return new ResponseEntity<>("Cannot assign", HttpStatus.BAD_REQUEST);
            }

            existingBox.setFundraiser(charityBoxService.setFundraiser(charityBoxAssignDTO.getFundraiser()));

            CharityBox updatedBox = charityBoxService.registerBox(existingBox);
            CharityBoxAssignedDTO assignedDTO = new CharityBoxAssignedDTO();
            assignedDTO.setIdentifier(id);
            assignedDTO.setFundraiser(new FundraiserBoxAssignedDTO(existingBox.getFundraiser().getName(), existingBox.getFundraiser().getCurrency().name()));
            return new ResponseEntity<>(assignedDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping
    public ResponseEntity<?> getAllBoxes() {
        try {
            List<CharityBoxReturnDTO> boxes = charityBoxService.getAllBoxes();
            return new ResponseEntity<>(boxes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //TODO: make fundriser an optional attribute?
    @PostMapping
    public ResponseEntity<?> addBox(@RequestBody CharityBoxCreateDTO charityBoxCreateDTO) {
        CharityBox charityBox = new CharityBox();
        charityBox.setIdentifier(charityBoxCreateDTO.getIdentifier());
        return new ResponseEntity<>(charityBoxService.createBox(charityBox), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBox(@PathVariable String id) {
        try{
            charityBoxService.removeBox(id);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(String.format("Box %s removed.", id), HttpStatus.OK);
    }

    @PutMapping("/donate")
    public ResponseEntity<?> donate( @RequestBody CollectedDTO collectedDTO) {
        try{
            charityBoxService.donate(collectedDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(String.format("Donated %f %s to %s.", collectedDTO.getAmount(), collectedDTO.getCurrency(), collectedDTO.getCharityBox()), HttpStatus.OK);

    }

    @PutMapping("/empty/{id}")
    public ResponseEntity<?> emptyBox(@PathVariable String id) {
        try {
            charityBoxService.empty(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(String.format("Box %s emptied.", id), HttpStatus.OK);
    }
}
