package org.example.sii_task.controllers;

import jakarta.validation.Valid;
import org.example.sii_task.errorHandling.DoesNotExist;
import org.example.sii_task.models.ApiError;
import org.example.sii_task.models.collected.CollectedDTO;
import org.example.sii_task.models.charityBox.*;
import org.example.sii_task.models.fundraiser.FundraiserBoxAssignedDTO;
import org.example.sii_task.services.CharityBoxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.RoundingMode;
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
    public ResponseEntity<?> updateCharityBox(@PathVariable String id, @Valid @RequestBody CharityBoxAssignDTO charityBoxAssignDTO) {
        Optional<CharityBox> optionalBox = charityBoxService.findById(id);
            if (optionalBox.isEmpty()) {
                throw new DoesNotExist("Charity Box with id " + id + " does not exist");
            }
            CharityBox existingBox = optionalBox.get();
            charityBoxService.canAssign(id);
            existingBox.setFundraiser(charityBoxService.setFundraiser(charityBoxAssignDTO.getFundraiser()));
            charityBoxService.registerBox(existingBox);
            CharityBoxAssignedDTO assignedDTO = new CharityBoxAssignedDTO();
            assignedDTO.setIdentifier(id);
            assignedDTO.setFundraiser(new FundraiserBoxAssignedDTO(existingBox.getFundraiser().getName(), existingBox.getFundraiser().getCurrency().name()));
            return new ResponseEntity<>(assignedDTO, HttpStatus.OK);
    }



    @GetMapping
    public ResponseEntity<?> getAllBoxes() {
        List<CharityBoxReturnDTO> boxes = charityBoxService.getAllBoxes();
        return new ResponseEntity<>(boxes, HttpStatus.OK);
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
        return new ResponseEntity<>(String.format("Box %s removed.", id), HttpStatus.OK);
    }


    //h
    @PutMapping("/donate")
    public ResponseEntity<?> donate(@Valid @RequestBody CollectedDTO collectedDTO) {
        charityBoxService.donate(collectedDTO);
        return new ResponseEntity<>(
                String.format("Donated %.2f %s to %s.",
                        collectedDTO.getAmount().setScale(2, RoundingMode.HALF_DOWN).doubleValue(),
                        collectedDTO.getCurrency(),
                        collectedDTO.getCharityBox()
                ),
                HttpStatus.OK
        );


    }

    @PutMapping("/empty/{id}")
    public ResponseEntity<?> emptyBox(@PathVariable String id) {
        charityBoxService.empty(id);
        return new ResponseEntity<>(String.format("Box %s emptied.", id), HttpStatus.OK);
    }
}
