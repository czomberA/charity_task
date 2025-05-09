package org.example.sii_task.services;

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
    @Autowired
    FundraiserRepository fundraiserRepository;

    public Fundraiser createFundraiser(Fundraiser fundraiser) throws Exception {
        Optional<Fundraiser> optionalFundraiser = fundraiserRepository.getFundraiserByName(fundraiser.getName());
        if (optionalFundraiser.isPresent()) {
            throw new Exception("Fundraiser already exist");
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
