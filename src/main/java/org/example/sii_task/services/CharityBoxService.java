package org.example.sii_task.services;

import org.example.sii_task.errorHandling.*;
import org.example.sii_task.models.collected.Collected;
import org.example.sii_task.models.collected.CollectedDTO;
import org.example.sii_task.models.charityBox.CharityBox;
import org.example.sii_task.models.charityBox.CharityBoxReturnDTO;
import org.example.sii_task.models.fundraiser.Fundraiser;
import org.example.sii_task.repositories.CharityBoxRepository;
import org.example.sii_task.repositories.CollectedRepository;
import org.example.sii_task.repositories.FundraiserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CharityBoxService {
    @Autowired
    CharityBoxRepository charityBoxRepository;
    @Autowired
    FundraiserRepository fundraiserRepository;
    @Autowired
    private CollectedRepository collectedRepository;
    @Autowired
    private CurrencyService currencyService;


    public Fundraiser setFundraiser(String fundraiser) {

        Optional<Fundraiser> existing = fundraiserRepository.getFundraiserByName(fundraiser);

        if (existing.isPresent()) {
            return existing.get();
        } else {
            throw new DoesNotExist(String.format("Fundraiser %s does not exist", fundraiser));
        }

    }

    public void registerBox(CharityBox charityBox) {
        charityBoxRepository.save(charityBox);
        fundraiserRepository.save(charityBox.getFundraiser());
    }

    public void canAssign(String id){
        Optional<CharityBox> box = charityBoxRepository.getCharityBoxByIdentifier(id);
        if (box.isEmpty()) {
            throw new DoesNotExist(String.format("Box %s does not exist", id));
        }
        CharityBox foundBox = box.get();
        if (foundBox.getFundraiser() != null) {
            throw new AlreadyAssigned("Already assigned to a fundraiser");
        }
        if (!foundBox.getCollections().isEmpty()) {
            throw new NotEmpty("Not empty");
        } else {
            System.out.println(foundBox.getCollections());
        }
    }

    public List<CharityBoxReturnDTO> getAllBoxes() {
        List<CharityBoxReturnDTO> boxes = new ArrayList<>();
        for (CharityBox charityBox : charityBoxRepository.findAll()) {
            CharityBoxReturnDTO boxDTO= new CharityBoxReturnDTO();
            boxDTO.setIdentifier(charityBox.getIdentifier());
            boxDTO.setAssigned(charityBox.getFundraiser() != null);
            boxDTO.setEmpty(charityBox.getCollections().isEmpty());
            boxes.add(boxDTO);
        }
        return boxes;
    }

    public Optional<CharityBox> findById(String id) {
        return charityBoxRepository.getCharityBoxByIdentifier(id);
    }

    public CharityBox createBox(CharityBox charityBox) {
        Optional<CharityBox> box = charityBoxRepository.getCharityBoxByIdentifier(charityBox.getIdentifier());
        if (box.isPresent()){
            throw new AlreadyExistsException("Box already exists");
        }
        return charityBoxRepository.save(charityBox);
    }

    public void removeBox(String id) {
        Optional<CharityBox> optionalBox = charityBoxRepository.getCharityBoxByIdentifier(id);
        if (optionalBox.isPresent()) {
            CharityBox box = optionalBox.get();
            Fundraiser fundraiser = box.getFundraiser();
            if (fundraiser != null) {
                fundraiser.getBoxes().remove(box);
                fundraiserRepository.save(fundraiser);
            }
            charityBoxRepository.delete(box);
        } else {
            throw new DoesNotExist("Box does not exist. May have been removed already.");
        }
    }

    public void donate(CollectedDTO collectedDTO) {
        Optional<CharityBox> optionalBox = charityBoxRepository.getCharityBoxByIdentifier(collectedDTO.getCharityBox());
        CharityBox box;
        if (optionalBox.isEmpty()) {
            throw new DoesNotExist("Box does not exist. Cannot donate.");
        }
        box = optionalBox.get();
        box.donate(collectedDTO.getAmount(), collectedDTO.getCurrency());
        charityBoxRepository.save(box);
    }

    public void empty(String id) {
        Optional<CharityBox> optionalBox = charityBoxRepository.getCharityBoxByIdentifier(id);

        if (optionalBox.isEmpty()) {
            throw new DoesNotExist("Box does not exist. Cannot empty.");
        }
        CharityBox box = optionalBox.get();
        if (box.getFundraiser() == null) {
            throw new NotAssigned("Box is not assigned. Cannot empty.");
        }
        if (box.getCollections().isEmpty()) {
            return;
        }

        String currencyOfFundraiser = box.getFundraiser().getCurrency().name();
        System.out.println(box.getCollections());
        List<Collected> collectionsToRemove = new ArrayList<>(box.getCollections());

        //It's this complex because of an API I use, please see ReadMe for details
        for (Collected c : box.getCollections()) {
            if (c.getCurrency().name().equals(currencyOfFundraiser)) {
                box.getFundraiser().donate(c.getAmount());
            } else {
                if (currencyOfFundraiser.equals("PLN")) {
                    BigDecimal err = currencyService.getRateToPln(c.getCurrency().name());
                    System.out.println(err);
                    box.getFundraiser().donate(c.getAmount().multiply(err).setScale(2, BigDecimal.ROUND_HALF_UP));

                } else if (c.getCurrency().name().equals("PLN")) {
                    BigDecimal err = currencyService.getRateFromPLN(currencyOfFundraiser, c.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    box.getFundraiser().donate(err);
                } else {
                    BigDecimal err = currencyService.convertCurrency(c.getCurrency().name(), currencyOfFundraiser, c.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    box.getFundraiser().donate(err);
                }
            }

        }
        for (Collected c : collectionsToRemove) {
            box.getCollections().remove(c);
            collectedRepository.delete(c);
        }

        box.getCollections().clear();
        charityBoxRepository.save(box);
        fundraiserRepository.save(box.getFundraiser());
    }
}
