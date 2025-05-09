package org.example.sii_task.services;

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


    public Fundraiser setFundraiser(String fundraiser) throws Exception {

        Optional<Fundraiser> existing = fundraiserRepository.getFundraiserByName(fundraiser);

        if (existing.isPresent()) {
            return existing.get();
        } else {
            throw new Exception("Fundraiser does not exist");
        }

    }

    public String setIdentifier(String identifier) throws Exception {
        Optional<CharityBox> existing = charityBoxRepository.getCharityBoxByIdentifier(identifier);
        if (existing.isPresent()) {
            return existing.get().getIdentifier();
        } else {
            throw new Exception("Box does not exist");
        }
    }

    public CharityBox registerBox(CharityBox charityBox) {
        charityBoxRepository.save(charityBox);
        fundraiserRepository.save(charityBox.getFundraiser());
        return charityBox;
    }

    public boolean canAssign(String id) throws Exception {
        Optional<CharityBox> box = charityBoxRepository.getCharityBoxByIdentifier(id);
        if (box.isEmpty()) {
            throw new Exception("Box does not exist");
        }
        CharityBox foundBox = box.get();
        if (foundBox.getFundraiser() != null) {
            throw new Exception("Already assigned fundraiser");
        }
        if (!foundBox.getCollections().isEmpty()) {
            throw new Exception("Not empty");
        } else {
            System.out.println(foundBox.getCollections());
        }
        return true;
    }

    public List<CharityBoxReturnDTO> getAllBoxes() {
        List<CharityBoxReturnDTO> boxes = new ArrayList<>();
        for (CharityBox charityBox : charityBoxRepository.findAll()) {
            CharityBoxReturnDTO boxDTO= new CharityBoxReturnDTO();
            boxDTO.setIdentifier(charityBox.getIdentifier());
            if (charityBox.getFundraiser() != null) {
                boxDTO.setAssigned(true);
            } else {
                boxDTO.setAssigned(false);
            }
            if (charityBox.getCollections().isEmpty()) {
                boxDTO.setEmpty(true);
            } else {
                boxDTO.setEmpty(false);
            }
            boxes.add(boxDTO);
        }
        return boxes;
    }

    public Optional<CharityBox> findById(String id) {
        return charityBoxRepository.getCharityBoxByIdentifier(id);
    }

    public CharityBox createBox(CharityBox charityBox) {
        //TODO: check if exists
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
            throw new RuntimeException("Box does not exist. May have been removed already.");
        }
    }

    public void donate(CollectedDTO collectedDTO) {
        Optional<CharityBox> optionalBox = charityBoxRepository.getCharityBoxByIdentifier(collectedDTO.getCharityBox());
        CharityBox box;
        if (optionalBox.isEmpty()) {
            throw new RuntimeException("Box does not exist. Cannot donate.");
        }

        box = optionalBox.get();
        if (box.getFundraiser() == null) {
            throw new RuntimeException("Box is not assigned to a fundraiser. Cannot donate.");
        }
        Collected c = box.donate(collectedDTO.getAmount(), collectedDTO.getCurrency());
        charityBoxRepository.save(box);
        //collectedRepository.save(c);
    }

    public void empty(String id) {
        Optional<CharityBox> optionalBox = charityBoxRepository.getCharityBoxByIdentifier(id);

        if (optionalBox.isEmpty()) {
            throw new RuntimeException("Box does not exist. Cannot empty.");
        }
        CharityBox box = optionalBox.get();
        if (box.getFundraiser() == null) {
            throw new RuntimeException("Box is not assigned. Cannot empty.");
        }
        if (box.getCollections().isEmpty()) {
            throw new RuntimeException("Box is empty. Nothing to transfer.");
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
                    double err = currencyService.getRateToPln(c.getCurrency().name());
                    box.getFundraiser().donate(c.getAmount()*err);

                } else if (c.getCurrency().name().equals("PLN")) {
                    double err = currencyService.getRateFromPLN(currencyOfFundraiser, c.getAmount());
                    box.getFundraiser().donate(err);
                } else {
                    double err = currencyService.convertCurrency(c.getCurrency().name(), currencyOfFundraiser, c.getAmount());
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
