package org.example.sii_task.services;

import org.example.sii_task.errorHandling.AlreadyExistsException;
import org.example.sii_task.models.fundraiser.Fundraiser;
import org.example.sii_task.models.fundraiser.FundraiserReturnDTO;
import org.example.sii_task.repositories.FundraiserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FundraiserService {

    private final FundraiserRepository fundraiserRepository;

    public FundraiserService(FundraiserRepository fundraiserRepository) {
        this.fundraiserRepository = fundraiserRepository;
    }

    public Fundraiser createFundraiser(Fundraiser fundraiser) {
        Optional<Fundraiser> optionalFundraiser = fundraiserRepository.getFundraiserByName(fundraiser.getName());
        if (optionalFundraiser.isPresent()) {
            throw new AlreadyExistsException("Fundraiser with name " + fundraiser.getName() + " already exists");
        }
        return fundraiserRepository.save(fundraiser);
    }

    public List<FundraiserReturnDTO> getFundraisers() {
        List<FundraiserReturnDTO> fundraiserReturnDTOs = new ArrayList<>();
        for (Fundraiser fundraiser : fundraiserRepository.findAll()) {
            FundraiserReturnDTO frDTO = new FundraiserReturnDTO();
            frDTO.setName(fundraiser.getName());
            frDTO.setAmount(fundraiser.getAccount());
            frDTO.setCurrency(fundraiser.getCurrency().name());
            fundraiserReturnDTOs.add(frDTO);
        }
        return fundraiserReturnDTOs;
    }
}
