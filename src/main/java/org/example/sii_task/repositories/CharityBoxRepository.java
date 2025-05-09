package org.example.sii_task.repositories;

import org.example.sii_task.models.charityBox.CharityBox;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CharityBoxRepository extends CrudRepository<CharityBox, Long> {
    Optional<CharityBox> getCharityBoxByIdentifier(String identifier);
}
