package org.example.sii_task.controllers;

import jakarta.validation.Valid;
import org.example.sii_task.models.fundraiser.Fundraiser;
import org.example.sii_task.models.fundraiser.FundraiserDTO;
import org.example.sii_task.models.fundraiser.FundraiserReturnDTO;
import org.example.sii_task.services.FundraiserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fundraiser")
public class FundraiserController {
    private final FundraiserService fundraiserService;


    public FundraiserController(FundraiserService fundraiserService) {
        this.fundraiserService = fundraiserService;
    }

    @PostMapping()
    public ResponseEntity<?> createFundraiser(@Valid @RequestBody FundraiserDTO fundraiserDTO) {
        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setName(fundraiserDTO.getName());
        fundraiser.setCurrency(fundraiserDTO.getCurrency());
        Fundraiser createdEvent = fundraiserService.createFundraiser(fundraiser);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);

    }

    @GetMapping()
    public ResponseEntity<List<FundraiserReturnDTO>> getFundraisers() {
        return new ResponseEntity<>(fundraiserService.getFundraisers(), HttpStatus.OK);
    }

}
